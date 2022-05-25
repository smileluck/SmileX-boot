package top.zsmile.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.meta.IPage;
import top.zsmile.meta.TableInfo;
import top.zsmile.utils.Constants;
import top.zsmile.utils.ReflectUtils;
import top.zsmile.utils.TableQueryUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public class BaseSelectProvider extends BaseProvider {

    /**
     * 根据主键ID查询数据
     *
     * @param context
     * @return
     */
    public String selectById(ProviderContext context, @Param(Constants.COLUMNS) final String... columns) {
        TableInfo tableInfo = getTableInfo(context);

        String s = new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
            WHERE(tableInfo.logicDelColumnSet());
            WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
        return s;
    }


    /**
     * 根据主键ID列表查询数据
     *
     * @param context
     * @return
     */
    public String selectBatchIds(ProviderContext context, @Param(Constants.COLLECTION) final Collection<? extends Serializable> ids, @Param(Constants.COLUMNS) final String... columns) {
        TableInfo tableInfo = getTableInfo(context);

        String sql = new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
            WHERE(tableInfo.logicDelColumnSet());
//            WHERE(tableInfo.getPrimaryColumn() + " in (" + Joiner.on(",").join(ids) + ")");
            WHERE(tableInfo.getPrimaryColumn() + " in <foreach item='item' collection='coll' open='(' separator=',' close=')'>#{item}</foreach>");
//            WHERE(tableInfo.getPrimaryColumn() + " in (" + TableQueryUtils.convertForeach("#{item}", "coll", null, "item", ",") + ")");
        }}.toString();

//        String str = "<script>SELECT %s FROM %s WHERE %s IN (%s) %s </script>";
//        String format = String.format(str, String.join(",", tableInfo.getSelectColumns()), tableInfo.getTableName(),
//                tableInfo.getPrimaryColumn(), TableQueryUtils.convertForeach("#{item}", "coll", null, "item", ","), "");

//        return format;

        return TableQueryUtils.getSqlScript(sql);
    }


    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     *
     * @param context
     * @param columnMap
     * @param columns
     * @return
     */
    public String selectByMap(ProviderContext context, @Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap, @Param(Constants.COLUMNS) final String... columns) {
        TableInfo tableInfo = getTableInfo(context);

        String s = new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
//            WHERE(tableInfo.getPrimaryColumn() + " in (" + Joiner.on(",").join(ids) + ")");
            WHERE(tableInfo.logicDelColumnSet());
            WHERE(TableQueryUtils.getMapCondition(columnMap));
        }}.toString();

        return TableQueryUtils.getSqlScript(s);
    }


    /**
     * 根据对象entity查询不为null的数据，可传入字段名查询需要得字段
     *
     * @param context
     * @param entity
     * @param columns
     * @return
     */
    public String selectList(ProviderContext context, @Param(Constants.ENTITY) Object entity, @Param(Constants.COLUMNS) final String... columns) {
        TableInfo tableInfo = getTableInfo(context);

        Field[] fields = tableInfo.getFields();

        String s = new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
            WHERE(tableInfo.logicDelColumnSet());
            WHERE(Stream.of(fields).filter(field -> ReflectUtils.getFieldValue(entity, field.getName()) != null).map(TableQueryUtils::getAssignParameter).toArray(String[]::new));
        }}.toString();

        return TableQueryUtils.getSqlScript(s);
    }

    /**
     * 根据对象entity查询不为null的数据，可传入字段名查询需要得字段
     *
     * @param context
     * @param columnMap
     * @return
     */
    public String selectCount(ProviderContext context, @Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap) {
        TableInfo tableInfo = getTableInfo(context);

        String s = new SQL() {{
            SELECT(tableInfo.getCountColumn());
            FROM(tableInfo.getTableName());
            WHERE(tableInfo.logicDelColumnSet());
            WHERE(TableQueryUtils.getMapCondition(columnMap));
        }}.toString();

        return TableQueryUtils.getSqlScript(s);
    }

    /**
     * 通用字段查询方法
     *
     * @param tableInfo
     * @param columns
     * @return
     */
    private String[] selectColumn(final TableInfo tableInfo, final String... columns) {
        return columns.length > 0 ? Stream.of(columns).map(TableQueryUtils::humpToLineName).toArray(String[]::new) : tableInfo.getSelectColumns();
    }


    /**
     * 根据对象entity查询不为null的数据，可传入字段名查询需要得字段
     *
     * @param context
     * @param columnMap
     * @return
     */
    public String selectCount(ProviderContext context, @Param(Constants.PAGE) IPage page, @Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap) {
        TableInfo tableInfo = getTableInfo(context);

        String s = new SQL() {{
            SELECT(tableInfo.getCountColumn());
            FROM(tableInfo.getTableName());
            WHERE(tableInfo.logicDelColumnSet());
            WHERE(TableQueryUtils.getMapCondition(columnMap));
            OFFSET(page.getOffset());
            LIMIT(page.getSize());
            ORDER_BY(page.getOrderColumn());
        }}.toString();

        return TableQueryUtils.getSqlScript(s);
    }
}
