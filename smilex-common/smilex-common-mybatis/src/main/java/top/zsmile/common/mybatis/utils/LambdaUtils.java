package top.zsmile.common.mybatis.utils;

import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.mybatis.meta.SFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lambda 解析工具
 * <p>
 * 通过 writeReplace 反射提取 {@link SerializedLambda} 的实现方法名，
 * 按 lambda 类名缓存"方法名"字符串（而非 SerializedLambda 对象，避免阻碍类卸载）。
 */
public final class LambdaUtils {

    private static final Map<String, String> METHOD_NAME_CACHE = new ConcurrentHashMap<>();

    private LambdaUtils() {
    }

    /**
     * 根据反射获取字段名称
     */
    public static <T, R> String getMethodName(SFunction<T, R> fn) {
        Class<? extends SFunction> aClass = fn.getClass();
        return METHOD_NAME_CACHE.computeIfAbsent(aClass.getName(), className -> {
            try {
                Method writeReplace = aClass.getDeclaredMethod("writeReplace");
                writeReplace.setAccessible(true);
                SerializedLambda lambda = (SerializedLambda) writeReplace.invoke(fn);
                return lambda.getImplMethodName();
            } catch (Exception e) {
                throw new SXException("lambda 方法名解析失败: " + className, e);
            }
        });
    }

    /**
     * 解析 lambda 对应的数据库列名（getUserName -> user_name）
     */
    public static <T, R> String getColumnName(SFunction<T, R> fn) {
        String methodName = getMethodName(fn);
        if (methodName.startsWith("is")) {
            return TableQueryUtils.humpToLineName(methodName.substring(2));
        } else if (methodName.startsWith("get")) {
            return TableQueryUtils.humpToLineName(methodName.substring(3));
        }
        return TableQueryUtils.humpToLineName(methodName);
    }
}
