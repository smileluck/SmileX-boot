package top.zsmile.mybatis.utils;

import top.zsmile.mybatis.meta.SFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LambdaUtils {

    private static final Map<String, SerializedLambda> lambdaMap = new ConcurrentHashMap<>();

    /**
     * 根据反射获取字段名称
     *
     * @param fn
     * @param <T>
     * @return
     */
    public static <T, R> String getMethodName(SFunction<T, R> fn) {
        Class<? extends SFunction> aClass = fn.getClass();
        SerializedLambda lambda = lambdaMap.computeIfAbsent(aClass.getName(), (item) -> {
            try {
                Method writeReplace = aClass.getDeclaredMethod("writeReplace");
                writeReplace.setAccessible(true);
                SerializedLambda invoke = (SerializedLambda) writeReplace.invoke(fn);
                return invoke;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        });
        if (lambda != null) {
            return lambda.getImplMethodName();
        } else {
            return null;
        }
    }

    public static <T, R> String getColumnName(SFunction<T, R> fn) {
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
