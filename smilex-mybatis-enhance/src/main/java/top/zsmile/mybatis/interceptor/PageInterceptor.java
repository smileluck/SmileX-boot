package top.zsmile.mybatis.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import top.zsmile.mybatis.meta.IPage;
import top.zsmile.mybatis.utils.Constants;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;


//@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class PageInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println(invocation);

        Object[] args = invocation.getArgs();
        Object target = invocation.getTarget();
        if (target instanceof Executor) {
            Executor executor = (Executor) invocation.getTarget();
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = args[1];
            RowBounds rowBounds = (RowBounds) args[2];
            Method method = invocation.getMethod();

            CacheKey cacheKey;
            BoundSql boundSql;
            //由于逻辑关系，只会进入一次
            if (args.length == 4) {
                //4 个参数时
                boundSql = ms.getBoundSql(parameter);
                cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
            } else {
                //6 个参数时
                cacheKey = (CacheKey) args[4];
                boundSql = (BoundSql) args[5];
            }
            if (isSelectPage(ms.getId())) {

                Configuration config = ms.getConfiguration();
                Connection connection = config.getEnvironment().getDataSource().getConnection();

                Map<String, Object> params = (Map<String, Object>) boundSql.getParameterObject();
                IPage page = (IPage) params.get(Constants.PAGE);

            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    private boolean isSelectPage(String method) {
        return method.endsWith(".selectPage");
    }
}
