package top.zsmile.common.mybatis.utils;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import java.util.Map;

/**
 * MyBatis SQL/BoundSql 工具
 */
public final class MybatisSqlUtils {

    private MybatisSqlUtils() {
    }

    /**
     * 基于原 BoundSql 构造一条新 SQL 的 BoundSql，复制参数映射与动态 SQL 追加参数
     */
    public static BoundSql copyBoundSql(Configuration configuration, BoundSql boundSql, String newSql) {
        BoundSql newBoundSql = new BoundSql(configuration, newSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        Map<String, Object> additionalParameters = getAdditionalParameters(boundSql);
        if (additionalParameters != null && !additionalParameters.isEmpty()) {
            for (Map.Entry<String, Object> entry : additionalParameters.entrySet()) {
                newBoundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
            }
        }
        return newBoundSql;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getAdditionalParameters(BoundSql boundSql) {
        try {
            MetaObject metaObject = SystemMetaObject.forObject(boundSql);
            return (Map<String, Object>) metaObject.getValue("additionalParameters");
        } catch (Exception e) {
            return null;
        }
    }
}
