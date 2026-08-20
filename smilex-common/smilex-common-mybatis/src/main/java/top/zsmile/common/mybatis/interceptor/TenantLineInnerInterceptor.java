package top.zsmile.common.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import top.zsmile.common.mybatis.annotation.TenantIgnore;
import top.zsmile.common.mybatis.cache.TableInfoCache;
import top.zsmile.common.mybatis.spi.TenantIdProvider;
import top.zsmile.common.mybatis.utils.MybatisSqlUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多租户 SQL 改写拦截器（依赖注入式）
 * <p>
 * 对 SELECT/UPDATE/DELETE 自动追加 {@code WHERE tenant_id = ?}（Long 值校验后内联，无注入风险）；
 * 表范围由 TableInfoCache 中带 tenantId 字段的实体自动识别，支持配置排除表；
 * Mapper 方法或类上标注 {@link TenantIgnore} 可跳过改写；
 * INSERT 的租户回填由 Provider 层（EntityAutoFill）完成，本拦截器不处理。
 * <p>
 * 注意：SQL 解析失败时记录错误日志并放行原 SQL（fail-open）。
 * 注册顺序须早于分页拦截器（先注册在内层），使 count SQL 同样被改写。
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class}),
})
public class TenantLineInnerInterceptor implements Interceptor {

    private final TenantIdProvider tenantIdProvider;
    private final Set<String> ignoreTables;
    /**
     * msId -> 是否跳过租户改写（@TenantIgnore 缓存）
     */
    private final Map<String, Boolean> ignoreCache = new ConcurrentHashMap<>();

    public TenantLineInnerInterceptor(TenantIdProvider tenantIdProvider, Set<String> ignoreTables) {
        this.tenantIdProvider = tenantIdProvider;
        this.ignoreTables = ignoreTables;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Long tenantId = tenantIdProvider == null ? null : tenantIdProvider.getTenantId();
        if (tenantId == null || isIgnored(ms) || ms.getSqlCommandType() == SqlCommandType.INSERT
                || ms.getSqlCommandType() == SqlCommandType.FLUSH) {
            return invocation.proceed();
        }

        Executor executor = (Executor) invocation.getTarget();
        Object parameter = args[1];
        boolean isQuery = "query".equals(invocation.getMethod().getName());
        // 预热 TableInfo：Provider SQL 在 executor 内部才构建，首次查询时需确保租户表反向索引可用
        warmTableInfo(ms);
        BoundSql boundSql = isQuery && args.length == 6 ? (BoundSql) args[5] : ms.getBoundSql(parameter);

        String newSql = rewrite(boundSql.getSql(), tenantId);
        if (newSql == null) {
            return invocation.proceed();
        }

        if (isQuery) {
            RowBounds rowBounds = (RowBounds) args[2];
            ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
            BoundSql newBoundSql = MybatisSqlUtils.copyBoundSql(ms.getConfiguration(), boundSql, newSql);
            CacheKey cacheKey = executor.createCacheKey(ms, parameter, rowBounds, newBoundSql);
            return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, newBoundSql);
        }
        // update（含逻辑删除生成的 UPDATE 与物理 DELETE）：以改写后的 SQL 构造 MappedStatement 副本执行
        MappedStatement rewriteMs = rewriteMappedStatement(ms, boundSql, newSql);
        return executor.update(rewriteMs, parameter);
    }

    /**
     * 构造携带改写后 SQL 的 MappedStatement 副本（其余属性原样复制）
     */
    private MappedStatement rewriteMappedStatement(MappedStatement ms, BoundSql boundSql, String newSql) {
        StaticSqlSource sqlSource = new StaticSqlSource(ms.getConfiguration(), newSql, boundSql.getParameterMappings());
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), sqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(String.join(",", ms.getKeyProperties()));
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /**
     * SQL 改写入口：解析并追加租户条件，无需改写时返回 null
     */
    private String rewrite(String sql, Long tenantId) {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            boolean changed = false;
            if (statement instanceof Select) {
                changed = processSelect((Select) statement, tenantId);
            } else if (statement instanceof Update) {
                changed = processUpdate((Update) statement, tenantId);
            } else if (statement instanceof Delete) {
                changed = processDelete((Delete) statement, tenantId);
            }
            return changed ? statement.toString() : null;
        } catch (Exception e) {
            log.error("租户拦截器 SQL 解析失败，原样放行: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 处理 SELECT（jsqlparser 4.9：PlainSelect/ParenthesedSelect/SetOperationList/WithItem 均直接/间接为 Select）
     */
    private boolean processSelect(Select select, Long tenantId) {
        boolean changed = false;
        List<WithItem> withItems = select.getWithItemsList();
        if (withItems != null) {
            for (WithItem withItem : withItems) {
                changed |= processSelect(withItem.getSelect(), tenantId);
            }
        }
        if (select instanceof PlainSelect) {
            PlainSelect plainSelect = (PlainSelect) select;
            changed |= processFromItem(plainSelect, tenantId);
            List<Join> joins = plainSelect.getJoins();
            if (joins != null) {
                for (Join join : joins) {
                    if (join.getRightItem() instanceof Table) {
                        Table table = (Table) join.getRightItem();
                        String tenantColumn = tenantColumn(table);
                        if (tenantColumn != null) {
                            Expression eq = buildTenantEquals(table, tenantColumn, tenantId);
                            Expression on = join.getOnExpression();
                            join.setOnExpression(on == null ? eq : new AndExpression(on, eq));
                            changed = true;
                        }
                    } else if (join.getRightItem() instanceof Select) {
                        changed |= processSelect((Select) join.getRightItem(), tenantId);
                    }
                }
            }
        } else if (select instanceof SetOperationList) {
            for (Select body : ((SetOperationList) select).getSelects()) {
                changed |= processSelect(body, tenantId);
            }
        } else if (select instanceof ParenthesedSelect) {
            changed |= processSelect(((ParenthesedSelect) select).getSelect(), tenantId);
        }
        return changed;
    }

    private boolean processFromItem(PlainSelect plainSelect, Long tenantId) {
        if (plainSelect.getFromItem() instanceof Table) {
            Table table = (Table) plainSelect.getFromItem();
            String tenantColumn = tenantColumn(table);
            if (tenantColumn != null) {
                Expression eq = buildTenantEquals(table, tenantColumn, tenantId);
                Expression where = plainSelect.getWhere();
                plainSelect.setWhere(where == null ? eq : new AndExpression(where, eq));
                return true;
            }
        } else if (plainSelect.getFromItem() instanceof Select) {
            return processSelect((Select) plainSelect.getFromItem(), tenantId);
        }
        return false;
    }

    private boolean processUpdate(Update update, Long tenantId) {
        String tenantColumn = tenantColumn(update.getTable());
        if (tenantColumn != null) {
            Expression eq = buildTenantEquals(update.getTable(), tenantColumn, tenantId);
            Expression where = update.getWhere();
            update.setWhere(where == null ? eq : new AndExpression(where, eq));
            return true;
        }
        return false;
    }

    private boolean processDelete(Delete delete, Long tenantId) {
        String tenantColumn = tenantColumn(delete.getTable());
        if (tenantColumn != null) {
            Expression eq = buildTenantEquals(delete.getTable(), tenantColumn, tenantId);
            Expression where = delete.getWhere();
            delete.setWhere(where == null ? eq : new AndExpression(where, eq));
            return true;
        }
        return false;
    }

    private Expression buildTenantEquals(Table table, String tenantColumn, Long tenantId) {
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(new Column(table, tenantColumn));
        equalsTo.setRightExpression(new LongValue(tenantId));
        return equalsTo;
    }

    /**
     * 表命中租户表则返回租户列名，否则 null
     */
    private String tenantColumn(Table table) {
        String normalized = TableInfoCache.normalizeTableName(table.getName());
        if (ignoreTables != null && ignoreTables.contains(normalized)) {
            return null;
        }
        return TableInfoCache.getTenantColumn(normalized);
    }

    /**
     * 预热 Mapper 对应的 TableInfo，确保租户表反向索引已建立（computeIfAbsent，重复调用开销极小）
     */
    private void warmTableInfo(MappedStatement ms) {
        try {
            int lastDot = ms.getId().lastIndexOf('.');
            TableInfoCache.getTableInfo(Class.forName(ms.getId().substring(0, lastDot)));
        } catch (Exception e) {
            // 非 BaseMapper 体系的语句（如 XML 手写 SQL），跳过
        }
    }

    /**
     * @TenantIgnore 逃生舱：按 MappedStatement id 反查 Mapper 方法/类注解并缓存
     */
    private boolean isIgnored(MappedStatement ms) {
        String id = ms.getId();
        Boolean cached = ignoreCache.get(id);
        if (cached != null) {
            return cached;
        }
        boolean ignored = false;
        try {
            int lastDot = id.lastIndexOf('.');
            Class<?> mapperClass = Class.forName(id.substring(0, lastDot));
            String methodName = id.substring(lastDot + 1);
            if (mapperClass.isAnnotationPresent(TenantIgnore.class)) {
                ignored = true;
            } else {
                for (Method method : mapperClass.getMethods()) {
                    if (method.getName().equals(methodName) && method.isAnnotationPresent(TenantIgnore.class)) {
                        ignored = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // 无法解析（如 XML statement 或非 mapper 类），不跳过
        }
        ignoreCache.put(id, ignored);
        return ignored;
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof Executor ? Plugin.wrap(target, this) : target;
    }
}
