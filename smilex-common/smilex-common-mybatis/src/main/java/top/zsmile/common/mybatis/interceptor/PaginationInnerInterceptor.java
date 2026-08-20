package top.zsmile.common.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.common.mybatis.utils.Constants;
import top.zsmile.common.mybatis.utils.MybatisSqlUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 物理分页拦截器（PageHelper 思路）
 * <p>
 * 检测 mapper 参数中的 {@link IPage}：自动执行 count 查询回填 total，
 * 并在原 SQL 尾部追加 {@code LIMIT offset,size}（offset/size 均为受控整数，无注入风险）。
 * size 为负数（如 {@link Constants#PAGE_ALL_OFFSET}）时跳过分页但仍统计总数。
 * <p>
 * 注册顺序须晚于租户拦截器（外层），使 count SQL 先经租户改写。
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class PaginationInnerInterceptor implements Interceptor {

    /**
     * count MappedStatement 缓存：msId -> countMs
     */
    private final Map<String, MappedStatement> countMsCache = new ConcurrentHashMap<>();

    private final int maxPageSize;

    public PaginationInnerInterceptor(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        if (ms.getSqlCommandType() != SqlCommandType.SELECT) {
            return invocation.proceed();
        }
        IPage<?> page = findPage(args[1]);
        if (page == null) {
            return invocation.proceed();
        }

        Executor executor = (Executor) invocation.getTarget();
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        @SuppressWarnings("unchecked")
        ResultHandler<Object> resultHandler = (ResultHandler<Object>) args[3];
        BoundSql boundSql = args.length == 6 ? (BoundSql) args[5] : ms.getBoundSql(parameter);

        // count 统计
        MappedStatement countMs = buildCountMappedStatement(ms);
        String countSql = "SELECT COUNT(*) FROM (" + boundSql.getSql() + ") SMILEX_COUNT_TABLE";
        BoundSql countBoundSql = MybatisSqlUtils.copyBoundSql(ms.getConfiguration(), boundSql, countSql);
        CacheKey countKey = executor.createCacheKey(countMs, parameter, rowBounds, countBoundSql);
        List<Object> countResult = executor.query(countMs, parameter, rowBounds, resultHandler, countKey, countBoundSql);
        long total = countResult.isEmpty() || countResult.get(0) == null
                ? 0 : ((Number) countResult.get(0)).longValue();
        page.setTotal(total);

        // 追加 LIMIT（size 为负表示查全部，仅统计总数）
        if (page.getSize() < 0) {
            Object result = invocation.proceed();
            page.setRecords((List) result);
            return result;
        }
        int size = Math.min(page.getSize(), maxPageSize);
        long offset = Math.max(page.getOffset(), 0);
        String limitSql = boundSql.getSql() + " LIMIT " + offset + "," + size;
        BoundSql pageBoundSql = MybatisSqlUtils.copyBoundSql(ms.getConfiguration(), boundSql, limitSql);
        CacheKey pageKey = executor.createCacheKey(ms, parameter, rowBounds, pageBoundSql);
        List<?> records = executor.query(ms, parameter, rowBounds, resultHandler, pageKey, pageBoundSql);
        page.setRecords((List) records);
        return records;
    }

    /**
     * 在 mapper 参数中查找 IPage 实例
     */
    private IPage<?> findPage(Object parameter) {
        if (parameter instanceof IPage) {
            return (IPage<?>) parameter;
        }
        if (parameter instanceof Map) {
            for (Object value : ((Map<?, ?>) parameter).values()) {
                if (value instanceof IPage) {
                    return (IPage<?>) value;
                }
            }
        }
        return null;
    }

    /**
     * 构建 count 专用 MappedStatement（结果类型 Long，不入全局注册表）
     */
    private MappedStatement buildCountMappedStatement(MappedStatement ms) {
        return countMsCache.computeIfAbsent(ms.getId(), id -> {
            ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), id + "-Count", Long.class,
                    Collections.emptyList()).build();
            MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), id + "-Count",
                    ms.getSqlSource(), SqlCommandType.SELECT);
            builder.resultMaps(new ArrayList<>(Collections.singletonList(resultMap)));
            return builder.build();
        });
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof Executor ? Plugin.wrap(target, this) : target;
    }
}
