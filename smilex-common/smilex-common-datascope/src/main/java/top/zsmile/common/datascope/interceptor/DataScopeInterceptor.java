package top.zsmile.common.datascope.interceptor;

import top.zsmile.common.datascope.DataScopeContentHolder;
import top.zsmile.common.datascope.DataScopeHandleFactory;
import top.zsmile.common.datascope.DataScopePerm;
import top.zsmile.common.datascope.handle.AbstractDataScopeHandle;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Objects;
import java.util.Properties;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/03/07/10:15
 * @ClassName: DataScopeInterceptor
 * @Description: DataScopeInterceptor
 */
//@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
//        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
@Slf4j
public class DataScopeInterceptor implements Interceptor {

    /**
     * 拦截方法
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof Executor) {
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            BoundSql boundSql = ms.getBoundSql(args[1]);
            DataScopePerm dataScopePerm = DataScopeContentHolder.get();
            if (Objects.nonNull(dataScopePerm) && dataScopePerm.getNeedFilter() && !dataScopePerm.getHandleKey().equals(AbstractDataScopeHandle.NIL)) {
                // 获取对应数据域的执行方法
                AbstractDataScopeHandle abstractDataScopeHandle = DataScopeHandleFactory.get(dataScopePerm.getHandleKey());
                String handleSql = abstractDataScopeHandle.handle(ms, boundSql, dataScopePerm);
                log.info("MappedStatement => {},BoundSQL => {},SQL => {}", ms, boundSql, boundSql.getSql());
//                    StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
//                    MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
//                    metaObject.setValue("delegate.boundSql.sql", handleSql);
                newMs(invocation, handleSql);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            DataScopePerm dataScopePerm = DataScopeContentHolder.get();
            if (Objects.nonNull(dataScopePerm) && dataScopePerm.getNeedFilter() && !dataScopePerm.getHandleKey().equals(AbstractDataScopeHandle.NIL)) {
                return Plugin.wrap(target, this);
            }
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

    /**
     * 构建新的 MapperStatement
     *
     * @param invocation 代理对象
     * @param newSql     新的SQL
     */
    private void newMs(Invocation invocation, String newSql) {
        // 获取指向参数对象
        final Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        BoundSql boundSql = mappedStatement.getBoundSql(args[1]);
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), newSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        // 通过反射更改属性值
        MappedStatement neMappedStatement = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
        MetaObject metaObject = SystemMetaObject.forObject(neMappedStatement);
        metaObject.setValue("sqlSource.boundSql.sql", newSql);
        // 更改指向新的 MapperStatement
        args[0] = neMappedStatement;
    }

    /**
     * 拷贝创建一个新的MapperStatement
     *
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        // 创建新的MapperStatement的构建器
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        // 拷贝属性
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                stringBuilder.append(keyProperty).append(",");
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            builder.keyProperty(stringBuilder.toString());
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
     * 实现自定义SqlSource
     */
    public static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
