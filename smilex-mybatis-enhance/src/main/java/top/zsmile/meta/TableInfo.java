package top.zsmile.meta;

import java.lang.reflect.Field;

public class TableInfo {
    private TableInfo() {
    }

    /**
     * 主键名
     */
    private static final String DEFAULT_PRIMARY_KEY = "id";

    /**
     * 表名
     */
    private String tableName;

    /**
     * 实体类型不含@NoColunm注解的field
     */
    private Field[] fields;

    /**
     * 主键列名
     */
    private String primaryColumn;


    /**
     * 所有列名
     */
    private String columns;


    /**
     * 所有select sql的列名，有带下划线的将其转为aa_bb AS aaBb
     */
    private String[] selectColumns;


    public static TableInfo of(Class<?> clazz) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.tableName = queryTableName(clazz);
        Field[] fields = clazz.getDeclaredFields();
        tableInfo.fields = fields;
        tableInfo.primaryColumn = queryPrimaryColumn(fields);
        tableInfo.selectColumns = querySelectColumn(fields);
        return tableInfo;
    }

    /**
     * TODO 查询类的TableName注解名称
     *
     * @return
     */
    private static String queryTableName(Class<?> clazz) {
        return null;
    }

    /**
     * TODO 查询主键列
     */
    private static String queryPrimaryColumn(Field[] fields) {
        return null;
    }

    /**
     * TODO 查询列名
     */
    private static String[] querySelectColumn(Field[] fields) {
        return null;
    }


}
