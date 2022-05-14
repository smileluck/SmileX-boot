package top.zsmile.utils;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import org.springframework.util.StringUtils;
import top.zsmile.annotation.TableField;
import top.zsmile.annotation.TableId;
import top.zsmile.annotation.TableLogic;
import top.zsmile.annotation.TableName;
import top.zsmile.common.utils.NameStyleUtils;
import top.zsmile.dao.BaseDao;
import top.zsmile.meta.TableInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TableQueryUtils {

    /**
     * 字段entity和 数据库字段对应
     */
    private static Map<String, String> columnNameMap = new ConcurrentHashMap<>();

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 主键名
     */
    private static final String DEFAULT_PRIMARY_KEY = "id";
    /**
     * 删除键名，1删除，0未删除
     */
    private static final String DEFAULT_DELETE_LOGIC_KEY = "del_flag";
    /**
     * 小数点 point
     */
    private static final String POINT = ".";


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
        return NameStyleUtils.humpToLine(clazz.getSimpleName().replace("Entity", ""));
    }

    /**
     * 转换Entity列表为下划线
     */
    public static String humpToLineName(Field field) {
        return humpToLineName(field.getName());
    }

    /**
     * 转换Entity列表为下划线
     */
    public static String humpToLineName(String fieldName) {
        String res = columnNameMap.computeIfAbsent(fieldName, k -> {
            StringBuffer sb = new StringBuffer();
            Matcher matcher = humpPattern.matcher(fieldName);
            while (matcher.find()) {
                matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
            }
            matcher.appendTail(sb);
            return sb.toString().toLowerCase().intern();
        });
        return res;
    }

    /**
     * 获取查询列名as实体类名
     */
    public static String getSelectColumn(Field field) {
        String s = humpToLineName(field);
        return (s + " AS " + field.getName()).intern();
    }

    /**
     * 注入字段,#{exampleColumn}
     *
     * @param field
     * @return
     */
    public static String getInjectParameter(Field field) {
        return getInjectParameter(field.getName());
    }

    /**
     * 注入字段,#{exampleColumn}
     *
     * @param fieldName
     * @return
     */
    public static String getInjectParameter(String fieldName) {
        return ("#{" + fieldName + "}").intern();
    }

    /**
     * 设置字段, example_column = #{exampleColumn}
     *
     * @param field
     * @return
     */
    public static String getAssignParameter(Field field) {
        return getAssignParameter(field);
    }

    /**
     * 设置字段, example_column = #{exampleColumn}
     *
     * @param fieldName
     * @return
     */
    public static String getAssignParameter(String fieldName) {
        return (humpToLineName(fieldName) + "=" + getInjectParameter(fieldName)).intern();
    }

    /**
     * 使用<Script></Script>包裹，使其中的特殊标签起作用
     *
     * @param sql
     * @return
     */
    public static String getSqlScript(String sql) {
        return Constants.SCRIPT_START + sql + Constants.SCRIPT_END;
    }


    /**
     * 使用map转换查询条件
     */
    public static String getMapCondition(TableInfo tableInfo, Map<String, Object> map) {
        Set<String> keySet = map.keySet();
        StringBuilder sb = new StringBuilder();
        for (String key : keySet) {
            if (sb.length() != 0) {
                sb.append(" and ");
            }
//            sb.append(tableInfo.getTableName() + POINT);
            sb.append(humpToLineName(key) + "=" + getInjectParameter(Constants.COLUMNS_MAP + POINT + key));
        }
        return sb.toString();
    }
}
