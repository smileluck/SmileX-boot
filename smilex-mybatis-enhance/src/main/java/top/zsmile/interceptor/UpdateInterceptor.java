package top.zsmile.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zsmile.api.common.CommonAuthApi;
import top.zsmile.entity.BaseEntity;

import java.sql.Statement;
import java.util.Date;
import java.util.Map;

@Component
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
public class UpdateInterceptor implements Interceptor {
    @Autowired
    private CommonAuthApi commonAuthApi;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println(invocation);
        if (invocation.getTarget() instanceof Executor) {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object params = invocation.getArgs()[1];
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            Map<String, Object> userInfo = commonAuthApi.queryUserInfo();
            if (sqlCommandType == SqlCommandType.UPDATE) {
                if (params instanceof BaseEntity) {
                    BaseEntity baseEntity = (BaseEntity) params;
                    Date date = new Date();
                    String username = userInfo.get("username").toString();
                    baseEntity.setUpdateTime(date);
                    baseEntity.setUpdateBy(username);
                } else if (params instanceof Map) {
                    Map baseMap = (Map) params;
                    Date date = new Date();
                    String username = userInfo.get("username").toString();
                    baseMap.put("updateTime", date);
                    baseMap.put("updateBy", username);
                }
            } else if (sqlCommandType == SqlCommandType.INSERT) {
                if (params instanceof BaseEntity) {
                    BaseEntity baseEntity = (BaseEntity) params;
                    Date date = new Date();
                    String username = userInfo.get("username").toString();
                    baseEntity.setCreateTime(date);
                    baseEntity.setCreateBy(username);
                    baseEntity.setUpdateTime(date);
                    baseEntity.setUpdateBy(username);
                } else if (params instanceof Map) {
                    Map baseMap = (Map) params;
                    Date date = new Date();
                    String username = userInfo.get("username").toString();
                    baseMap.put("createTime", date);
                    baseMap.put("createBy", username);
                    baseMap.put("updateTime", date);
                    baseMap.put("updateBy", username);
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
