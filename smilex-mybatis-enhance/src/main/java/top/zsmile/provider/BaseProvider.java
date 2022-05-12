package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import top.zsmile.common.utils.NameStyleUtils;
import top.zsmile.meta.TableInfo;
import top.zsmile.utils.ReflectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BaseProvider {
    private static final Map<Class<?>, TableInfo> TABLE_CACHE = new ConcurrentHashMap<>();

    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();

    protected TableInfo getTableInfo(ProviderContext providerContext) {
        TableInfo tableInfo = TABLE_CACHE.computeIfAbsent(providerContext.getMapperType(), TableInfo::of);
        return tableInfo;
    }

    protected void validNullId(Object obj, TableInfo tableInfo) {
        if (ReflectUtils.getFieldValue(obj, NameStyleUtils.lineToHump(tableInfo.getPrimaryColumn(), false)) == null) {
            throw new IllegalArgumentException("主键不能为空");
        }
    }
}
