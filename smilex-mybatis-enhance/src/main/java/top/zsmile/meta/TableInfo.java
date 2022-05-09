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
    private Long tableName;

    /**
     * 实体类型不含@NoColunm注解的field
     */
    private Field[] fields;

    /**
     * 主键列名
     */
    private String primaryKeyColumn;


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
        return tableInfo;
    }
}
