package top.zsmile.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;

@Slf4j
public class ReflectUtils {
    public static Object getFieldValue(Object obj, String fieldName) {
        Class clazz = obj.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field declaredField = clazz.getDeclaredField(fieldName);
                if (declaredField != null) {
                    declaredField.setAccessible(true);
                    return declaredField.get(obj);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error(clazz.getName() + "no find name => " + fieldName);
            }
        }
        return null;
    }
}
