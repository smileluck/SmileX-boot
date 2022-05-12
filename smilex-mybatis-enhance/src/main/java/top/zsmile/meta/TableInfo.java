package top.zsmile.meta;

import org.springframework.util.StringUtils;
import top.zsmile.annotation.TableName;
import top.zsmile.dao.BaseDao;
import top.zsmile.utils.TableQueryUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.stream.Stream;

public class TableInfo {
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
     * 主键列名
     */
    private String primaryColumn;

    /**
     * 所有select sql的列名，有带下划线的将其转为aa_bb AS aaBb
     */
    private String[] selectColumns;


    public static TableInfo of(Class<?> clazz) {
        TableInfo tableInfo = new TableInfo();
        Class<?> aClass = TableQueryUtils.queryEntity(clazz);
        tableInfo.tableName = TableQueryUtils.queryTableName(aClass);
        Field[] fields = TableQueryUtils.queryExistColumn(aClass);
        tableInfo.fields = fields;
        tableInfo.primaryColumn = TableQueryUtils.queryPrimaryColumn(fields);
        tableInfo.selectColumns = TableQueryUtils.querySelectColumn(fields);
        return tableInfo;
    }


    public String getTableName() {
        return tableName;
    }

    public String getPrimaryColumn() {
        return primaryColumn;
    }

    public String[] getSelectColumns() {
        return selectColumns;
    }

    public Field[] getFields() {
        return fields;
    }

    public String primaryColumnWhere() {
        return primaryColumn + "= #{" + primaryColumn + "}";
    }
}
