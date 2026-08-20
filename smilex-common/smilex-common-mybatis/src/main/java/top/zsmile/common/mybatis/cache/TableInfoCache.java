package top.zsmile.common.mybatis.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import top.zsmile.common.mybatis.meta.TableInfo;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表信息缓存
 * <p>
 * key 既可以是 Mapper 接口，也可以是实体类（TableInfo.of 自行识别）。
 * 另维护 表名 -> 租户列 反向索引，供租户拦截器按 SQL 中的表名查租户列。
 */
@Slf4j
public class TableInfoCache {
    private static final Map<Class<?>, TableInfo> TABLE_CACHE = new ConcurrentHashMap<>();
    /**
     * 表名(小写) -> 租户列
     */
    private static final Map<String, String> TENANT_TABLE_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取表信息
     *
     * @param providerContext
     * @return
     */
    public static TableInfo getTableInfo(ProviderContext providerContext) {
        return getTableInfo(providerContext.getMapperType());
    }

    /**
     * 获取表信息
     *
     * @param clazz
     * @return
     */
    public static TableInfo getTableInfo(Class clazz) {
        TableInfo tableInfo = TABLE_CACHE.computeIfAbsent(clazz, TableInfo::of);
        cacheTenantTable(tableInfo);
        return tableInfo;
    }

    private static void cacheTenantTable(TableInfo tableInfo) {
        if (tableInfo.hasTenantColumn()) {
            TENANT_TABLE_CACHE.putIfAbsent(tableInfo.getTableName().toLowerCase(), tableInfo.getTenantColumn());
        }
    }

    /**
     * 表是否为租户表，是则返回租户列名，否则返回 null
     */
    public static String getTenantColumn(String tableName) {
        if (tableName == null) {
            return null;
        }
        return TENANT_TABLE_CACHE.get(normalizeTableName(tableName));
    }

    /**
     * 去除表名的反引号/双引号并转小写
     */
    public static String normalizeTableName(String name) {
        String n = name;
        if (n.startsWith("`") || n.startsWith("\"")) {
            n = n.substring(1);
        }
        if (n.endsWith("`") || n.endsWith("\"")) {
            n = n.substring(0, n.length() - 1);
        }
        return n.toLowerCase();
    }

    /**
     * 初始化表信息
     *
     * @param map
     * @return
     */
    public static void initTableInfo(Map<String, Class> map) {
        Set<String> keySet = map.keySet();
        for (String s : keySet) {
            Class aClass = map.get(s);
            log.debug("mybatis-enhance init table class ==> " + aClass);
            getTableInfo(aClass);
        }
    }
}
