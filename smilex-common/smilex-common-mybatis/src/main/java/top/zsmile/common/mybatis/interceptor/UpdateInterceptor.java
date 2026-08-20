package top.zsmile.common.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import top.zsmile.api.system.common.CommonAuthApi;
import top.zsmile.common.mybatis.entity.BaseEntity;
import top.zsmile.common.web.utils.SpringContextUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 审计字段兜底填充拦截器
 * <p>
 * 框架自带 CRUD（Provider 路径）的审计填充已由 EntityAutoFill 在 SQL 构建前完成，
 * 本拦截器作为手写 XML 语句的兜底：对 BaseEntity（含 ParamMap 集合中的实体）补齐审计字段。
 * 用户名取自 CommonAuthApi（无登录上下文回退 "system"）。
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
public class UpdateInterceptor implements Interceptor {

    private static final String FALLBACK_USERNAME = "system";

    private volatile CommonAuthApi commonAuthApi;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object params = invocation.getArgs()[1];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (sqlCommandType == SqlCommandType.INSERT || sqlCommandType == SqlCommandType.UPDATE
                || sqlCommandType == SqlCommandType.DELETE) {
            operateTime(params, sqlCommandType);
        }
        return invocation.proceed();
    }

    private void operateTime(Object params, SqlCommandType sqlCommandType) {
        if (params instanceof Map) {
            // 多参数/集合场景：遍历 ParamMap 值中的实体集合逐个填充
            for (Object value : ((Map<?, ?>) params).values()) {
                if (value instanceof List) {
                    for (Object item : (List<?>) value) {
                        fillEntity(item, sqlCommandType);
                    }
                }
            }
        } else {
            fillEntity(params, sqlCommandType);
        }
    }

    private void fillEntity(Object entity, SqlCommandType sqlCommandType) {
        if (!(entity instanceof BaseEntity)) {
            return;
        }
        BaseEntity baseEntity = (BaseEntity) entity;
        LocalDateTime now = LocalDateTime.now();
        String username = queryUsername();
        if (sqlCommandType == SqlCommandType.INSERT) {
            if (baseEntity.getCreateTime() == null) {
                baseEntity.setCreateTime(now);
            }
            if (baseEntity.getCreateBy() == null) {
                baseEntity.setCreateBy(username);
            }
        }
        baseEntity.setUpdateTime(now);
        baseEntity.setUpdateBy(username);
    }

    private String queryUsername() {
        try {
            CommonAuthApi api = this.commonAuthApi;
            if (api == null) {
                api = SpringContextUtils.getBean(CommonAuthApi.class);
                if (api == null) {
                    return FALLBACK_USERNAME;
                }
                this.commonAuthApi = api;
            }
            Object username = api.queryUserInfo().get("username");
            return Objects.toString(username, FALLBACK_USERNAME);
        } catch (Exception e) {
            return FALLBACK_USERNAME;
        }
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof Executor ? Plugin.wrap(target, this) : target;
    }
}
