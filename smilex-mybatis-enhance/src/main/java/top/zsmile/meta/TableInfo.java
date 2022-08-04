package top.zsmile.meta;

import top.zsmile.common.utils.NameStyleUtils;
import top.zsmile.common.utils.StringPool;
import top.zsmile.utils.TableQueryUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableInfo implements Serializable {


    private static final int LOGIC_DEL_NUMBER = 1;

    private static final int LOGIC_NOT_DEL_NUMBER = 0;

    private TableInfo() {
    }


    /**
     * 表名
     */
    private String tableName;

    /**
     * 实体类型不含@NoColunm注解的field
     */
    private Field[] fields;

    /**
     * 所有列
     */
    private String[] columns;

    /**
     * 主键列名
     */
    private String primaryColumn;

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
     * 注入参数列表
     */
    private List<String> whereColumn;

    public static TableInfo of(Class<?> clazz) {
        TableInfo tableInfo = new TableInfo();
        Class<?> aClass = TableQueryUtils.queryEntity(clazz);
        tableInfo.tableName = TableQueryUtils.queryTableName(aClass);
        Field[] fields = TableQueryUtils.queryExistColumn(aClass);
        tableInfo.fields = fields;
        tableInfo.logicDelColumn = TableQueryUtils.queryLogicDelColumn(fields);
        tableInfo.columns = TableQueryUtils.queryColumn(fields);
        tableInfo.allColumnsSql = String.join(StringPool.COMMA, tableInfo.getColumns());
        tableInfo.primaryColumn = TableQueryUtils.queryPrimaryColumn(fields);
        tableInfo.countColumn = (StringPool.COUNT + StringPool.LEFT_BRACKET + tableInfo.primaryColumn + StringPool.RIGHT_BRACKET).intern();
        tableInfo.selectColumns = TableQueryUtils.querySelectColumn(fields);
        tableInfo.injectParameter = TableQueryUtils.queryInjectParameter(fields);
        tableInfo.whereColumn = TableQueryUtils.queryWhereColumn(fields);
        return tableInfo;
    }

    public String getTableName() {
        return tableName;
    }

    public String getPrimaryColumn() {
        return primaryColumn;
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

    public String getLogicDelColumn() {
        return logicDelColumn;
    }

    public String primaryColumnWhere() {
        return (primaryColumn + StringPool.EQUALS + StringPool.HASH_LEFT_BRACE + NameStyleUtils.lineToHump(primaryColumn, false) + StringPool.RIGHT_BRACE).intern();
    }

    public String logicDelColumnSet() {
        return logicDelColumn + StringPool.EQUALS + LOGIC_DEL_NUMBER;
    }

    public boolean hasLogicDelColumn() {
        return logicDelColumn != null;
    }

    public String logicDelColumnWhere() {
        return logicDelColumn + StringPool.EQUALS + LOGIC_NOT_DEL_NUMBER;
    }

    public String[] getInjectParameter() {
        return injectParameter;
    }

    public String getAllSelectColumn() {
        return null;
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
}
