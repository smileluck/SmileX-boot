package top.zsmile.meta;

import org.springframework.util.StringUtils;
import top.zsmile.annotation.TableName;
import top.zsmile.common.utils.NameStyleUtils;
import top.zsmile.dao.BaseDao;
import top.zsmile.utils.TableQueryUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.stream.Stream;

public class TableInfo {

    private static final int LOGIC_DEL_NUMBER = 1;

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
     * 逻辑删除字段
     *
     * @param clazz
     * @return
     */
    private String logicDelColumn;

    /**
     * 注入参数列表
     *
     * @param clazz
     * @return
     */
    private String[] injectParameter;


    public static TableInfo of(Class<?> clazz) {
        TableInfo tableInfo = new TableInfo();
        Class<?> aClass = TableQueryUtils.queryEntity(clazz);
        tableInfo.tableName = TableQueryUtils.queryTableName(aClass);
        Field[] fields = TableQueryUtils.queryExistColumn(aClass);
        tableInfo.fields = fields;
        tableInfo.columns = TableQueryUtils.queryColumn(fields);
        tableInfo.primaryColumn = TableQueryUtils.queryPrimaryColumn(fields);
        tableInfo.logicDelColumn = TableQueryUtils.queryLogicDelColumn(fields);
        tableInfo.selectColumns = TableQueryUtils.querySelectColumn(fields);
        tableInfo.injectParameter = TableQueryUtils.queryInjectParameter(fields);
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
        return (primaryColumn + " = #{" + NameStyleUtils.lineToHump(primaryColumn, false) + "}").intern();
    }

    public String logicDelColumnSet() {
        return logicDelColumn + " = " + LOGIC_DEL_NUMBER;
    }

    public String[] getInjectParameter() {
        return injectParameter;
    }
}
