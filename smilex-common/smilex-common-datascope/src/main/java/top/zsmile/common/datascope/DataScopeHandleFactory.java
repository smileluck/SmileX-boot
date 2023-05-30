package top.zsmile.common.datascope;

import top.zsmile.common.datascope.handle.AbstractDataScopeHandle;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据处理器工厂
 *
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/02/24/15:15
 * @ClassName: DataScopeFactory
 * @Description: DataScopeFactory
 */
public class DataScopeHandleFactory {
    private final static ConcurrentHashMap<String, AbstractDataScopeHandle> handleMap = new ConcurrentHashMap<>();

    public static AbstractDataScopeHandle get(String key) {
        return handleMap.get(key);
    }

    public static void set(String key, AbstractDataScopeHandle handler) {
        Assert.notNull(key, "数据域的Key不能为空");
        Assert.notNull(handler, "数据域的处理不能为空");
        handleMap.put(key, handler);
    }
}
