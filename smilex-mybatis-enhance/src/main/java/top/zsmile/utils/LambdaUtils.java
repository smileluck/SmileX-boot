package top.zsmile.utils;

import top.zsmile.meta.SFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LambdaUtils {
    private static Map<String, String> columnSelectMap = new ConcurrentHashMap<>();

    /**
     * 根据反射获取字段名称
     *
     * @param fn
     * @param <T>
     * @return
     */
    public <T> String getMethodName(SFunction<T> fn) {
        try {
            Method writeReplace = fn.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            SerializedLambda invoke = (SerializedLambda) writeReplace.invoke(fn);
            return invoke.getImplMethodName();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> String getColumnName(SFunction<T> fn) {
        String methodName = getMethodName(fn);
        if (methodName == null) {
            throw new RuntimeException("lambda转换异常");
        }

        if (methodName.startsWith("is")) {
            return TableQueryUtils.humpToLineName(methodName.substring(2));
        } else if (methodName.startsWith("get")) {
            return TableQueryUtils.humpToLineName(methodName.substring(3));
        } else {
            return TableQueryUtils.humpToLineName(methodName);
        }
    }
}
