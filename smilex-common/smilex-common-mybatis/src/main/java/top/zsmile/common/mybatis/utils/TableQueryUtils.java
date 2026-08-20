package top.zsmile.common.mybatis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.mybatis.annotation.*;
import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.common.mybatis.meta.TableInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class TableQueryUtils {

    /**
     * 字段entity和 数据库字段对应
     */
    private static Map<String, String> columnNameMap = new ConcurrentHashMap<>();
    /**
     * 字段entity和 查询字段对应
     */
    private static Map<String, String> columnSelectMap = new ConcurrentHashMap<>();

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 合法列名/排序字段字符白名单（字母数字下划线点号），拦截列名拼接注入
     */
    private static final Pattern SQL_NAME_PATTERN = Pattern.compile("^[A-Za-z_][A-Za-z0-9_.]*$");

    /**
     * map 条件中框架自用的保留键，不参与实体字段校验
     */
    private static final Set<String> RESERVED_MAP_KEYS = new HashSet<>(Arrays.asList(
            Constants.PAGE, Constants.SIZE, "current", "offset", "orderColumn", "asc"));

    /**
     * 校验列名合法性，防止通过列名拼接注入 SQL
     *
     * @param name 列名（驼峰或下划线均可）
     * @return 校验通过的列名
     */
    public static String checkSqlName(String name) {
        if (name == null || !SQL_NAME_PATTERN.matcher(name).matches()) {
            throw new SXException("非法的列名参数: [" + name + "]");
        }
        return name;
    }


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
                .filter(type -> type.getRawType() == BaseMapper.class)
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
        List<Field> beforeFilterFields = ReflectUtils.queryThisAndSuperClassColumn(clazz);
        Field[] fields = beforeFilterFields.stream().filter(field -> {
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
     * 查询所有where条件的所有列
     */
    public static List<String> queryWhereColumn(Field[] fields) {
        List<String> collect = Stream.of(fields).map(Field::getName).collect(Collectors.toList());
        return collect;
    }


    /**
     * 查询所有列名
     */
    public static String[] queryHumpColumn(Field[] fields) {
        return Stream.of(fields).map(TableQueryUtils::humpToLineName).toArray(String[]::new);
    }

    /**
     * 查询主键列
     */
    public static String queryPrimaryColumn(Field[] fields) {
        return Stream.of(fields).filter(field -> field.isAnnotationPresent(TableId.class)).findFirst().map(TableQueryUtils::humpToLineName).orElse(Constants.DEFAULT_PRIMARY_KEY);
    }

    /**
     * 查询逻辑删除列
     */
    public static String queryLogicDelColumn(Field[] fields) {
        return queryColumnByAnno(fields, TableLogic.class, Constants.DEFAULT_DELETE_LOGIC_KEY);
    }

    /**
     * 查询租户ID列
     */
    public static String queryTenantColumn(Field[] fields) {
        return queryColumnByAnno(fields, TenantId.class, Constants.DEFAULT_TENANT_ID_KEY);
    }

    /**
     * 根据注解查询字段，存在就返回字段，否则返回对应默认值
     */
    public static String queryColumnByAnno(Field[] fields, Class clazz, String defaultStr) {
        Optional<Field> optional = Stream.of(fields).filter(field -> field.isAnnotationPresent(clazz)).findFirst();
        if (optional.isPresent()) {
            return optional.map(TableQueryUtils::humpToLineName).get();
        } else {
            Optional<Field> first = Stream.of(fields).filter(field -> field.getName().equals(defaultStr)).findFirst();
            if (first.isPresent()) {
                return first.map(TableQueryUtils::humpToLineName).get();
            }
        }
        return null;
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
        return humpToLineName(clazz.getSimpleName().replace("Entity", ""));
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
                if (matcher.start() > 0) {
                    matcher.appendReplacement(sb, StringPool.UNDERSCORE + matcher.group(0).toLowerCase());
                } else {
                    matcher.appendReplacement(sb, matcher.group(0));
                }
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
        return getSelectColumn(field.getName());
    }

    /**
     * 获取查询列名as实体类名
     */
    public static String getSelectColumn(String fieldName) {
        String res = columnSelectMap.computeIfAbsent(fieldName, item -> {
            String s = humpToLineName(fieldName);
            return s + " AS " + fieldName;
        });
        return res;
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
     * @param field
     * @param prefix 前缀
     * @return
     */
    public static String getInjectParameter(Field field, String prefix) {
        return getInjectParameter(field.getName(), prefix);
    }

    /**
     * 注入字段,#{exampleColumn}
     *
     * @param fieldName
     * @return
     */
    public static String getInjectParameter(String fieldName) {
        return getInjectParameter(fieldName, null);
    }

    /**
     * 注入字段,#{exampleColumn}
     *
     * @param fieldName
     * @param prefix    前缀
     * @return
     */
    public static String getInjectParameter(String fieldName, String prefix) {
        if (prefix != null) {
            return (StringPool.HASH_LEFT_BRACE + prefix + fieldName + StringPool.RIGHT_BRACE).intern();
        } else {
            return (StringPool.HASH_LEFT_BRACE + fieldName + StringPool.RIGHT_BRACE).intern();
        }
    }

    /**
     * 设置字段, example_column = #{exampleColumn}
     *
     * @param field
     * @return
     */
    public static String getAssignParameter(Field field) {
        return getAssignParameter(field.getName());
    }

    /**
     * 设置字段, example_column = #{exampleColumn}
     *
     * @param fieldName
     * @return
     */
    public static String getAssignParameter(String fieldName) {
        return (humpToLineName(fieldName) + StringPool.EQUALS + getInjectParameter(fieldName)).intern();
    }

    /**
     * 设置字段（带参数前缀）, example_column = #{prefix.exampleColumn}
     *
     * @param fieldName
     * @param prefix    参数前缀，如 "et."
     * @return
     */
    public static String getAssignParameter(String fieldName, String prefix) {
        return (humpToLineName(fieldName) + StringPool.EQUALS + getInjectParameter(fieldName, prefix)).intern();
    }

    /**
     * 使用<Script></Script>包裹，使其中的特殊标签起作用
     *
     * @param sql
     * @return
     */
    public static String getSqlScript(String sql) {
        log.debug(sql);
        return Constants.SCRIPT_START + sql + Constants.SCRIPT_END;
    }


    /**
     * 使用map转换查询条件
     * <p>
     * 空值键跳过（无过滤语义）；框架保留键跳过；
     * 其余未命中实体字段白名单的键直接抛异常（fail-fast），
     * 避免拼错字段名导致条件静默丢失、查出全表数据。
     */
    public static String getMapCondition(TableInfo tableInfo, Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        Set<String> keySet = map.keySet();
        StringBuilder sb = new StringBuilder();
        for (String key : keySet) {
            if (StringUtils.isEmpty(map.get(key))) {
                continue;
            }
            if (RESERVED_MAP_KEYS.contains(key)) {
                continue;
            }
            if (!tableInfo.hasWhereColumn(key)) {
                throw new SXException("查询字段[" + key + "]不存在于实体[" + tableInfo.getTableName()
                        + "]，请检查查询参数或改用正确的实体字段名");
            }
            if (sb.length() != 0) {
                sb.append(StringPool.AND_SPACE);
            }
            sb.append(checkSqlName(humpToLineName(key)) + StringPool.EQUALS + getInjectParameter(Constants.COLUMNS_MAP + StringPool.DOT + key));
        }
        return sb.toString();
    }


    /**
     * <p>
     * 生成 foreach 标签的脚本
     * </p>
     *
     * @param sqlScript  foreach 内部的 sql 脚本
     * @param collection collection
     * @param index      index
     * @param item       item
     * @param separator  separator
     * @return foreach 脚本
     */
    public static String convertForeach(final String sqlScript, final String collection, final String index,
                                        final String item, final String separator) {
        StringBuilder sb = new StringBuilder("<foreach");
        if (!StringUtils.isEmpty(collection)) {
            sb.append(" collection=\"").append(collection).append(StringPool.QUOTE);
        }
        if (!StringUtils.isEmpty(index)) {
            sb.append(" index=\"").append(index).append(StringPool.QUOTE);
        }
        if (!StringUtils.isEmpty(item)) {
            sb.append(" item=\"").append(item).append(StringPool.QUOTE);
        }
        if (!StringUtils.isEmpty(separator)) {
            sb.append(" separator=\"").append(separator).append(StringPool.QUOTE);
        }
        return sb.append(StringPool.RIGHT_CHEV).append(StringPool.NEWLINE).append(sqlScript).append(StringPool.NEWLINE).append("</foreach>").toString();
    }
}
