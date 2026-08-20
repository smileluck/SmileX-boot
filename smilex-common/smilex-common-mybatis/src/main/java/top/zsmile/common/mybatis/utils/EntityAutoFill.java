package top.zsmile.common.mybatis.utils;

import lombok.extern.slf4j.Slf4j;
import top.zsmile.api.system.common.CommonAuthApi;
import top.zsmile.common.core.utils.uuid.SnowFlake;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.spi.TenantIdProvider;
import top.zsmile.common.web.utils.SpringContextUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 实体自动填充（主键/租户/逻辑删除默认值/审计字段/字段加密）
 * <p>
 * 由 Provider 在构建 SQL 前调用；雪花算法与租户提供者由
 * {@code MybatisPluginConfiguration} 启动时注入，未注入时回退到安全默认值。
 */
@Slf4j
public final class EntityAutoFill {

    private static volatile SnowFlake snowFlake = new SnowFlake(0, 0);
    private static volatile TenantIdProvider tenantIdProvider;

    private EntityAutoFill() {
    }

    /**
     * 注入单例雪花算法（workerId/dataCenterId 来自配置）
     */
    public static void initGenerator(SnowFlake generator) {
        if (generator != null) {
            snowFlake = generator;
        }
    }

    /**
     * 注入租户ID提供者
     */
    public static void initTenantProvider(TenantIdProvider provider) {
        tenantIdProvider = provider;
    }

    /**
     * 插入前填充：主键(雪花)、租户ID、delFlag=0、审计四字段、加密字段
     */
    public static void fillInsert(Object entity, TableInfo tableInfo) {
        if (entity == null) {
            return;
        }
        // 主键：为空或 0 时生成雪花ID
        Field primaryField = tableInfo.getField(tableInfo.getPrimaryProperty());
        if (primaryField != null) {
            try {
                Object id = primaryField.get(entity);
                if (id == null || "0".equals(String.valueOf(id))) {
                    primaryField.set(entity, snowFlake.nextId());
                }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("主键填充失败: " + e.getMessage());
            }
        }
        // 租户ID：为空或 0 时从提供者取值
        if (tableInfo.hasTenantColumn()) {
            String tenantProperty = toHump(tableInfo.getTenantColumn());
            Field tenantField = tableInfo.getField(tenantProperty);
            if (tenantField != null) {
                try {
                    Object tenantId = tenantField.get(entity);
                    if (tenantId == null || "0".equals(String.valueOf(tenantId))) {
                        Long value = queryTenantId();
                        if (value != null) {
                            tenantField.set(entity, value);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("租户填充失败: " + e.getMessage());
                }
            }
        }
        // 逻辑删除默认值
        if (tableInfo.hasLogicDelColumn()) {
            setIfNull(entity, tableInfo, toHump(tableInfo.getLogicDelColumn()), 0);
        }
        // 审计字段
        setIfNull(entity, tableInfo, "createTime", nowValue(entity, tableInfo, "createTime"));
        setIfNull(entity, tableInfo, "updateTime", nowValue(entity, tableInfo, "updateTime"));
        String username = queryUsername();
        setIfNull(entity, tableInfo, "createBy", username);
        setIfNull(entity, tableInfo, "updateBy", username);
        // 字段加密
        FieldEncryptor.encrypt(entity, tableInfo);
    }

    /**
     * 更新前填充：updateTime/updateBy 审计字段、加密字段
     */
    public static void fillUpdate(Object entity, TableInfo tableInfo) {
        if (entity == null) {
            return;
        }
        setValue(entity, tableInfo, "updateTime", nowValue(entity, tableInfo, "updateTime"));
        setValue(entity, tableInfo, "updateBy", queryUsername());
        FieldEncryptor.encrypt(entity, tableInfo);
    }

    private static void setIfNull(Object entity, TableInfo tableInfo, String property, Object value) {
        if (value == null) {
            return;
        }
        Field field = tableInfo.getField(property);
        if (field == null) {
            return;
        }
        try {
            if (field.get(entity) == null) {
                field.set(entity, value);
            }
        } catch (IllegalAccessException e) {
            log.warn("字段填充失败: {}.{}, {}", tableInfo.getTableName(), property, e.getMessage());
        }
    }

    private static void setValue(Object entity, TableInfo tableInfo, String property, Object value) {
        if (value == null) {
            return;
        }
        Field field = tableInfo.getField(property);
        if (field == null) {
            return;
        }
        try {
            field.set(entity, value);
        } catch (IllegalAccessException e) {
            log.warn("字段填充失败: {}.{}, {}", tableInfo.getTableName(), property, e.getMessage());
        }
    }

    /**
     * 按实体字段声明的时间类型生成当前时间
     */
    private static Object nowValue(Object entity, TableInfo tableInfo, String property) {
        Field field = tableInfo.getField(property);
        if (field == null) {
            return null;
        }
        if (field.getType() == LocalDateTime.class) {
            return LocalDateTime.now();
        }
        if (field.getType() == Date.class) {
            return new Date();
        }
        return null;
    }

    private static String queryUsername() {
        try {
            CommonAuthApi authApi = SpringContextUtils.getBean(CommonAuthApi.class);
            if (authApi != null) {
                Object username = authApi.queryUserInfo().get("username");
                if (username != null) {
                    return username.toString();
                }
            }
        } catch (Exception e) {
            // 无 Spring 上下文或未登录场景，回退系统账号
        }
        return "system";
    }

    private static Long queryTenantId() {
        TenantIdProvider provider = tenantIdProvider;
        if (provider != null) {
            return provider.getTenantId();
        }
        try {
            CommonAuthApi authApi = SpringContextUtils.getBean(CommonAuthApi.class);
            if (authApi != null) {
                return authApi.queryTenantId();
            }
        } catch (Exception e) {
            // 无上下文，视为无租户
        }
        return null;
    }

    private static String toHump(String line) {
        StringBuilder sb = new StringBuilder();
        boolean upper = false;
        for (char c : line.toCharArray()) {
            if (c == '_') {
                upper = true;
            } else if (upper) {
                sb.append(Character.toUpperCase(c));
                upper = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
