package top.zsmile.common.mybatis.meta;

import top.zsmile.common.core.utils.NameStyleUtils;
import top.zsmile.common.mybatis.annotation.FieldEncrypt;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表元数据信息
 * <p>
 * 支持以 Mapper 接口或实体类两种方式构建：传入接口时自动解析 BaseMapper 泛型实体。
 */
public class TableInfo {

    private static final int LOGIC_DEL_NUMBER = 1;
    private static final int LOGIC_NOT_DEL_NUMBER = 0;

    private TableInfo() {
    }

    /**
     * 表名
     */
    private String tableName;

    /**
     * 租户ID列
     */
    private String tenantColumn;

    /**
     * 实体类
     */
    private Class<?> entityClass;

    /**
     * 实体字段（已 setAccessible，剔除 @TableField(exist=false)/static/final）
     */
    private Field[] fields;

    /**
     * 驼峰属性名 -> Field
     */
    private Map<String, Field> fieldMap;

    /**
     * 带 @FieldEncrypt 注解的字段
     */
    private List<Field> encryptFields;

    /**
     * 所有列
     */
    private String[] columns;

    /**
     * 主键列名
     */
    private String primaryColumn;

    /**
     * 主键驼峰属性名
     */
    private String primaryProperty;

    /**
     * 所有select sql的列名，有带下划线的将其转为aa_bb AS aaBb
     */
    private String[] selectColumns;

    /**
     * 所有select的列名，全部缓存
     */
    private String allColumnsSql;

    /**
     * 逻辑删除字段
     */
    private String logicDelColumn;

    /**
     * count 字段
     */
    private String countColumn;

    /**
     * 注入参数列表
     */
    private String[] injectParameter;

    /**
     * 可作为查询条件的驼峰字段名白名单
     */
    private List<String> whereColumn;

    public static TableInfo of(Class<?> clazz) {
        TableInfo tableInfo = new TableInfo();
        Class<?> aClass = clazz.isInterface() && BaseMapper.class.isAssignableFrom(clazz)
                ? TableQueryUtils.queryEntity(clazz) : clazz;
        tableInfo.entityClass = aClass;
        tableInfo.tableName = TableQueryUtils.queryTableName(aClass);
        Field[] fields = TableQueryUtils.queryExistColumn(aClass);
        for (Field field : fields) {
            field.setAccessible(true);
        }
        tableInfo.fields = fields;
        tableInfo.fieldMap = new HashMap<>(fields.length * 2);
        tableInfo.encryptFields = new ArrayList<>();
        for (Field field : fields) {
            tableInfo.fieldMap.put(field.getName(), field);
            if (field.isAnnotationPresent(FieldEncrypt.class)) {
                tableInfo.encryptFields.add(field);
            }
        }
        tableInfo.logicDelColumn = TableQueryUtils.queryLogicDelColumn(fields);
        tableInfo.columns = TableQueryUtils.queryColumn(fields);
        tableInfo.allColumnsSql = String.join(StringPool.COMMA, tableInfo.getColumns());
        tableInfo.primaryColumn = TableQueryUtils.queryPrimaryColumn(fields);
        tableInfo.primaryProperty = NameStyleUtils.lineToHump(tableInfo.primaryColumn, false);
        tableInfo.countColumn = (StringPool.COUNT + StringPool.LEFT_BRACKET + tableInfo.primaryColumn + StringPool.RIGHT_BRACKET).intern();
        tableInfo.selectColumns = TableQueryUtils.querySelectColumn(fields);
        tableInfo.injectParameter = TableQueryUtils.queryInjectParameter(fields);
        tableInfo.whereColumn = TableQueryUtils.queryWhereColumn(fields);
        tableInfo.tenantColumn = TableQueryUtils.queryTenantColumn(fields);
        return tableInfo;
    }

    public String getTableName() {
        return tableName;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getPrimaryColumn() {
        return primaryColumn;
    }

    public String getPrimaryProperty() {
        return primaryProperty;
    }

    public String getTenantColumn() {
        return tenantColumn;
    }

    public String[] getColumns() {
        return columns;
    }

    public String[] getSelectColumns() {
        return selectColumns;
    }

    public Field[] getFields() {
        return fields;
    }

    public Field getField(String propertyName) {
        return fieldMap.get(propertyName);
    }

    public List<Field> getEncryptFields() {
        return encryptFields;
    }

    public boolean hasEncryptField() {
        return !encryptFields.isEmpty();
    }

    public String getLogicDelColumn() {
        return logicDelColumn;
    }

    public String primaryColumnWhere() {
        return (primaryColumn + StringPool.EQUALS + StringPool.HASH_LEFT_BRACE + primaryProperty + StringPool.RIGHT_BRACE).intern();
    }

    public String logicDelColumnSet() {
        return logicDelColumn + StringPool.EQUALS + LOGIC_DEL_NUMBER;
    }

    public boolean hasLogicDelColumn() {
        return logicDelColumn != null;
    }

    public boolean hasTenantColumn() {
        return tenantColumn != null;
    }

    public String logicDelColumnWhere() {
        return logicDelColumn + StringPool.EQUALS + LOGIC_NOT_DEL_NUMBER;
    }

    public String[] getInjectParameter() {
        return injectParameter;
    }

    public String getAllColumnsSql() {
        return allColumnsSql;
    }

    public String getCountColumn() {
        return countColumn;
    }

    public boolean hasWhereColumn(String key) {
        return this.whereColumn.contains(key);
    }

    /**
     * 是否存在指定驼峰属性名对应的字段
     */
    public boolean hasField(String propertyName) {
        return fieldMap.containsKey(propertyName);
    }

    /**
     * 判断是否为 static/final 字段（内部构建使用）
     */
    static boolean isNonInstanceField(Field field) {
        return Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers());
    }

    @Override
    public String toString() {
        return "TableInfo{" + "tableName='" + tableName + '\'' + ", entityClass=" + entityClass.getName()
                + ", primaryColumn='" + primaryColumn + '\'' + ", fields=" + Arrays.toString(columns) + '}';
    }
}
