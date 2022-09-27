package top.zsmile.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import top.zsmile.meta.TableInfo;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表信息缓存
 */
@Slf4j
public class TableInfoCache {
    private static final Map<Class<?>, TableInfo> TABLE_CACHE = new ConcurrentHashMap<>();

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
        return TABLE_CACHE.computeIfAbsent(clazz, TableInfo::of);
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
            log.info("mybatis-enhance init table class ==> " + aClass);
            getTableInfo(aClass);
        }

    }


}
