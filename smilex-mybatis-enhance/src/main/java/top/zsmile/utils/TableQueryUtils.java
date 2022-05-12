package top.zsmile.utils;

import org.springframework.util.StringUtils;
import top.zsmile.annotation.TableField;
import top.zsmile.annotation.TableId;
import top.zsmile.annotation.TableLogic;
import top.zsmile.annotation.TableName;
import top.zsmile.common.utils.NameStyleUtils;
import top.zsmile.dao.BaseDao;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class TableQueryUtils {

    /**
     * 主键名
     */
    private static final String DEFAULT_PRIMARY_KEY = "id";
    /**
     * 删除键名，1删除，0未删除
     */
    private static final String DEFAULT_DELETE_LOGIC_KEY = "del_flag";

    /**
     * 查询实例传入的Entity类型
     *
     * @param clazz
     * @return
     */
    public static Class<?> queryEntity(Class<?> clazz) {
        return Stream.of(clazz.getGenericInterfaces())
                .filter(ParameterizedType.class::isInstance)
                .map(ParameterizedType.class::cast)
                .filter(type -> type.getRawType() == BaseDao.class)
                .findFirst()
                .map(type -> type.getActualTypeArguments()[0])
                .filter(Class.class::isInstance).map(Class.class::cast)
                .orElseThrow(() -> new IllegalStateException("未找到BaseMapper的泛型类 " + clazz.getName() + "."));
    }

    /**
     * 查询类的TableName注解名称
     *
     * @return
     */
    public static String queryTableName(Class<?> clazz) {
        TableName annotation = clazz.getAnnotation(TableName.class);
        if (annotation != null && !StringUtils.isEmpty(annotation.value())) {
            return annotation.value();
        } else {
            return convertEntityName(clazz);
        }
    }

    /**
     * 查询@TableField(exist=false),final/static之外的所有字段
     */
    public static Field[] queryExistColumn(Class<?> clazz) {
        Field[] fields = Arrays.stream(clazz.getDeclaredFields()).filter(field -> {
            TableField tableField = field.getAnnotation(TableField.class);
            if ((tableField != null && !tableField.exist()) || Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                return false;
            } else {
                return true;
            }
        }).toArray(Field[]::new);
        return fields;
    }

    /**
     * 查询所有列名
     */
    public static String[] queryColumn(Field[] fields) {
        return Stream.of(fields).map(TableQueryUtils::humpToLineName).toArray(String[]::new);
    }

    /**
     * 查询主键列
     */
    public static String queryPrimaryColumn(Field[] fields) {
        return Stream.of(fields).filter(field -> field.isAnnotationPresent(TableId.class)).findFirst().map(TableQueryUtils::humpToLineName).orElse(DEFAULT_DELETE_LOGIC_KEY);
    }

    /**
     * 查询逻辑删除列
     */
    public static String queryLogicDelColumn(Field[] fields) {
        return Stream.of(fields).filter(field -> field.isAnnotationPresent(TableLogic.class)).findFirst().map(TableQueryUtils::humpToLineName).orElse(DEFAULT_DELETE_LOGIC_KEY);
    }

    /**
     * 查询列名，example_column as exampleColumn
     */
    public static String[] querySelectColumn(Field[] fields) {
        return Stream.of(fields).map(TableQueryUtils::getSelectColumn).toArray(String[]::new);
    }


    /**
     * 注入参数名，#{exampleColumn}
     */
    public static String[] queryInjectParameter(Field[] fields) {
        return Stream.of(fields).map(TableQueryUtils::getInjectParameter).toArray(String[]::new);
    }

    /**
     * 转换Entity类名称为数据表名
     */
    public static String convertEntityName(Class<?> clazz) {
//        return clazz.getName
        return NameStyleUtils.humpToLine(clazz.getSimpleName().replace("Entity", ""));
    }

    /**
     * 转换Entity列表为下划线
     */
    public static String humpToLineName(Field field) {
        return NameStyleUtils.humpToLine(field.getName()).intern();
    }

    /**
     * 获取查询列名as实体类名
     */
    public static String getSelectColumn(Field field) {
        String s = humpToLineName(field);
        return (s + " AS " + field.getName()).intern();
    }

    public static String getInjectParameter(Field field) {
        return ("#{" + field.getName() + "}").intern();
    }

    public static String getAssignParameter(Field field) {
        return (humpToLineName(field) + "=" + getInjectParameter(field)).intern();
    }
}
