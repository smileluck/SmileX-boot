package top.zsmile.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;
import top.zsmile.entity.BaseEntity;

import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

@Component
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
public class UpdateInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println(invocation);

        if (invocation.getTarget() instanceof Executor) {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object arg = invocation.getArgs()[1];
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            if (sqlCommandType == SqlCommandType.UPDATE) {
                BaseEntity baseEntity = (BaseEntity) arg;
                Date date = new Date();
                baseEntity.setUpdateTime(date);
            } else if (sqlCommandType == SqlCommandType.INSERT) {
                BaseEntity baseEntity = (BaseEntity) arg;
                Date date = new Date();
                baseEntity.setCreateTime(date);
                baseEntity.setUpdateTime(date);
            }
        }
        return invocation.proceed();
    }
}
