package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import top.zsmile.cache.TableInfoCache;
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

    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();

    /**
     * 获取表信息
     *
     * @param providerContext
     * @return
     */
    protected TableInfo getTableInfo(ProviderContext providerContext) {
        TableInfo tableInfo = TableInfoCache.getTableInfo(providerContext);
        return tableInfo;
    }

    protected void validNullId(Object obj, TableInfo tableInfo) {
        if (ReflectUtils.getFieldValue(obj, NameStyleUtils.lineToHump(tableInfo.getPrimaryColumn(), false)) == null) {
            throw new IllegalArgumentException("主键不能为空");
        }
    }
}
