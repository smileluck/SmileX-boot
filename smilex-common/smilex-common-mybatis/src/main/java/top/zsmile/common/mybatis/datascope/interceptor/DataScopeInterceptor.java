package top.zsmile.common.mybatis.datascope.interceptor;

import top.zsmile.api.system.common.CommonAuthApi;
import top.zsmile.common.mybatis.datascope.DataScopeContentHolder;
import top.zsmile.common.mybatis.datascope.DataScopeHandleFactory;
import top.zsmile.common.mybatis.datascope.DataScopePerm;
import top.zsmile.common.mybatis.datascope.handle.AbstractDataScopeHandler;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 数据域拦截器
 *
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
public class DataScopeInterceptor implements Interceptor {

    @Resource
    private CommonAuthApi commonAuthApi;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof Executor) {
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            BoundSql boundSql = ms.getBoundSql(args[1]);

            DataScopePerm dataScopePerm = DataScopeContentHolder.get();
            if (Objects.nonNull(dataScopePerm) && dataScopePerm.getNeedFilter() && !dataScopePerm.getHandleKey().equals(AbstractDataScopeHandler.NIL)) {
                Map<String, Object> admin = commonAuthApi.queryUserInfo();
                if (admin != null) {
                    AbstractDataScopeHandler abstractDataScopeHandler = DataScopeHandleFactory.get(dataScopePerm.getHandleKey());
                    String handleSql = abstractDataScopeHandler.handle(ms, boundSql, dataScopePerm, admin);
                    newMs(invocation, handleSql);
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            DataScopePerm dataScopePerm = DataScopeContentHolder.get();
            if (Objects.nonNull(dataScopePerm) && dataScopePerm.getNeedFilter() && !dataScopePerm.getHandleKey().equals(AbstractDataScopeHandler.NIL)) {
                return Plugin.wrap(target, this);
            }
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private void newMs(Invocation invocation, String newSql) {
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
        MappedStatement neMappedStatement = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
        MetaObject metaObject = SystemMetaObject.forObject(neMappedStatement);
        metaObject.setValue("sqlSource.boundSql.sql", newSql);
        args[0] = neMappedStatement;
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
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
