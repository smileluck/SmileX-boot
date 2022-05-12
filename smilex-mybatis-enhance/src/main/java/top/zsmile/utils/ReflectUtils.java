package top.zsmile.utils;

import java.lang.reflect.Field;

public class ReflectUtils {
    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
