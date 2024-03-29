package top.zsmile.common.mybatis.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ReflectUtils {
    /**
     * 如果存在字段，则设置值
     *
     * @param obj        对象
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @return
     */
    public static boolean setFieldValue(Object obj, String fieldName, Object fieldValue) {
        Class clazz = obj.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field declaredField = clazz.getDeclaredField(fieldName);
                if (declaredField != null) {
                    declaredField.setAccessible(true);
                    declaredField.set(obj, fieldValue);
                    return true;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
//                log.error(clazz.getName() + " no find name => " + fieldName);
            }
        }
        return false;
    }

    /**
     * 根据字段名获取值
     *
     * @param obj
     * @param fieldName
     * @return
     */
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
//                log.error(clazz.getName() + " no find name => " + fieldName);
            }
        }
        return null;
    }

    /**
     * 根据字段获取对象值
     *
     * @param obj
     * @param field
     * @return
     */
    public static Object getFieldValue(Object obj, Field field) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 查询当前类及父级所有类的字段
     */
    public static List<Field> queryThisAndSuperClassColumn(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] declaredFields = clazz.getDeclaredFields();
            fields.addAll(Arrays.asList(declaredFields));
        }
        return fields;
    }
}
