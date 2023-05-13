package top.zsmile.common.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import top.zsmile.api.common.CommonAuthApi;
import top.zsmile.common.mybatis.entity.BaseEntity;
import top.zsmile.common.mybatis.utils.ReflectUtils;

import java.time.LocalDateTime;
import java.util.Map;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
public class UpdateInterceptor implements Interceptor {
    @Autowired
    private CommonAuthApi commonAuthApi;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof Executor) {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object params = invocation.getArgs()[1];
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            operateTime(params, sqlCommandType);
        }
        return invocation.proceed();
    }

    private void operateTime(Object params, SqlCommandType sqlCommandType) {
        Map<String, Object> userInfo = commonAuthApi.queryUserInfo();
        if (sqlCommandType == SqlCommandType.UPDATE || sqlCommandType == SqlCommandType.DELETE) {
            LocalDateTime now = LocalDateTime.now();
            String username = userInfo.get("username").toString();
            if (params instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) params;
                baseEntity.setUpdateTime(now);
                baseEntity.setUpdateBy(username);
            } else if (params instanceof Map) {
                Map baseMap = (Map) params;
                baseMap.put("updateTime", now);
                baseMap.put("updateBy", username);
            } else {
                ReflectUtils.setFieldValue(params, "updateTime", now);
            }
        } else if (sqlCommandType == SqlCommandType.INSERT) {
            LocalDateTime now = LocalDateTime.now();
            String username = userInfo.get("username").toString();
            if (params instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) params;
                baseEntity.setCreateTime(now);
                baseEntity.setCreateBy(username);
                baseEntity.setUpdateTime(now);
                baseEntity.setUpdateBy(username);
            } else if (params instanceof Map) {
                Map baseMap = (Map) params;
                baseMap.put("createTime", now);
                baseMap.put("createBy", username);
            } else {
                ReflectUtils.setFieldValue(params, "createTime", now);
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
