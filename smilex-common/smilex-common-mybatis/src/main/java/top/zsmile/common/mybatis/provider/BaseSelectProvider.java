package top.zsmile.common.mybatis.provider;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.zsmile.common.mybatis.holder.TenantHolder;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.utils.Constants;
import top.zsmile.common.mybatis.utils.ReflectUtils;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

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
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            if (tableInfo.hasTenantColumn() && TenantHolder.isIgnore()) WHERE(tableInfo.tenantColumnWhere());
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
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            if (tableInfo.hasTenantColumn() && TenantHolder.isIgnore()) WHERE(tableInfo.tenantColumnWhere());
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
     * @param cm
     * @param columns
     * @return
     */
    public String selectListByMap(ProviderContext context, final Map<String, Object> cm, final String... columns) {
        TableInfo tableInfo = getTableInfo(context);

        String s = new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
//            WHERE(tableInfo.getPrimaryColumn() + " in (" + Joiner.on(",").join(ids) + ")");
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            if (tableInfo.hasTenantColumn() && TenantHolder.isIgnore()) WHERE(tableInfo.tenantColumnWhere());
            if (!CollectionUtils.isEmpty(cm)) {
                String mapCondition = TableQueryUtils.getMapCondition(tableInfo, cm);
                if (!StringUtils.isEmpty(mapCondition)) {
                    WHERE(mapCondition);
                }
            }
        }}.toString();

        return TableQueryUtils.getSqlScript(s);
    }


    /**
     * 根据字段集合查询，可查询某个字段的集合
     *
     * @param context
     * @param cm
     * @param column
     * @return
     */
    public String selectSingleByMap(ProviderContext context, final Map<String, Object> cm, final String column) {
        TableInfo tableInfo = getTableInfo(context);

        String s = new SQL() {{
            SELECT(TableQueryUtils.getSelectColumn(column));
            FROM(tableInfo.getTableName());
//            WHERE(tableInfo.getPrimaryColumn() + " in (" + Joiner.on(",").join(ids) + ")");
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            if (tableInfo.hasTenantColumn() && TenantHolder.isIgnore()) WHERE(tableInfo.tenantColumnWhere());
            if (!CollectionUtils.isEmpty(cm)) {
                String mapCondition = TableQueryUtils.getMapCondition(tableInfo, cm);
                if (!StringUtils.isEmpty(mapCondition)) {
                    WHERE(mapCondition);
                }
            }
        }}.toString();

        return s;
    }


    /**
     * 根据对象entity查询不为null的数据，可传入字段名查询需要得字段
     *
     * @param context
     * @param entity
     * @param columns
     * @return
     */
    public String selectList(ProviderContext context, final Object entity, final String... columns) {
        TableInfo tableInfo = getTableInfo(context);

        Field[] fields = tableInfo.getFields();

        String s = new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            if (tableInfo.hasTenantColumn() && TenantHolder.isIgnore()) WHERE(tableInfo.tenantColumnWhere());
            if (entity != null) {
                WHERE(Stream.of(fields).filter(field -> ReflectUtils.getFieldValue(entity, field) != null).map(TableQueryUtils::getAssignParameter).toArray(String[]::new));
            }
        }}.toString();

        return TableQueryUtils.getSqlScript(s);
    }

    /**
     * 根据对象entity查询不为null的数据，可传入字段名查询需要得字段
     *
     * @param context
     * @param params
     * @return
     */
    public String selectCount(ProviderContext context, Map<String, Object> params) {
        Map<String, Object> cm = (Map<String, Object>) params.get(Constants.COLUMNS_MAP);
        TableInfo tableInfo = getTableInfo(context);

        String s = new SQL() {{
            SELECT(tableInfo.getCountColumn());
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            if (tableInfo.hasTenantColumn() && TenantHolder.isIgnore()) WHERE(tableInfo.tenantColumnWhere());
            if (!CollectionUtils.isEmpty(cm)) {
                String mapCondition = TableQueryUtils.getMapCondition(tableInfo, cm);
                if (!StringUtils.isEmpty(mapCondition)) {
                    WHERE(mapCondition);
                }
            }
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
        return columns.length > 0 ? Stream.of(ArrayUtils.add(columns, tableInfo.getPrimaryColumn())).map(TableQueryUtils::getSelectColumn).toArray(String[]::new) : tableInfo.getSelectColumns();
    }


    /**
     * 根据对象entity查询不为null的数据，可传入字段名查询需要得字段
     *
     * @param context
     * @param cm
     * @return
     */
    public String selectPage(ProviderContext context, final IPage page, final Map<String, Object> cm, final String... columns) {
        TableInfo tableInfo = getTableInfo(context);

        String s = new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            if (tableInfo.hasTenantColumn() && TenantHolder.isIgnore()) WHERE(tableInfo.tenantColumnWhere());
            if (!CollectionUtils.isEmpty(cm)) {
                String mapCondition = TableQueryUtils.getMapCondition(tableInfo, cm);
                if (!StringUtils.isEmpty(mapCondition)) {
                    WHERE(mapCondition);
                }
            }
            if (page.getSize() != Constants.PAGE_ALL_OFFSET) {
                OFFSET(page.getOffset());
                LIMIT(page.getSize());
            }
//            if (page.getOrderColumn() != null) {
//                ORDER_BY(page.getOrderColumn());
//            }
        }}.toString();

        return TableQueryUtils.getSqlScript(s);
    }
}
