package top.zsmile.common.mybatis.provider;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.zsmile.common.core.exception.SXException;
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
     */
    public String selectById(ProviderContext context, @Param(Constants.COLUMNS) final String... columns) {
        TableInfo tableInfo = getTableInfo(context);
        return new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
    }

    /**
     * 根据主键ID列表查询数据
     */
    public String selectBatchIds(ProviderContext context, @Param(Constants.COLLECTION) final Collection<? extends Serializable> ids, @Param(Constants.COLUMNS) final String... columns) {
        TableInfo tableInfo = getTableInfo(context);
        String sql = new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            WHERE(tableInfo.getPrimaryColumn() + " in <foreach item='item' collection='coll' open='(' separator=',' close=')'>#{item}</foreach>");
        }}.toString();
        return TableQueryUtils.getSqlScript(sql);
    }

    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     * <p>
     * map 的 key 必须命中实体字段白名单，否则 fail-fast 抛异常（见 TableQueryUtils#getMapCondition）
     */
    public String selectListByMap(ProviderContext context, final Map<String, Object> cm, final String... columns) {
        TableInfo tableInfo = getTableInfo(context);
        String s = new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
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
     * 根据字段集合查询某个字段的集合
     */
    public String selectSingleByMap(ProviderContext context, final Map<String, Object> cm, final String column) {
        TableInfo tableInfo = getTableInfo(context);
        // 列名需命中实体字段白名单，防止列名注入
        if (!tableInfo.hasWhereColumn(column)) {
            throw new SXException("查询字段[" + column + "]不存在于实体[" + tableInfo.getTableName() + "]");
        }
        String s = new SQL() {{
            SELECT(TableQueryUtils.getSelectColumn(column));
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
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
     * 根据对象entity查询不为null的数据（占位符带 et. 前缀，方法签名必须为 @Param(Constants.ENTITY)）
     */
    public String selectList(ProviderContext context, @Param(Constants.ENTITY) final Object entity, @Param(Constants.COLUMNS) final String... columns) {
        TableInfo tableInfo = getTableInfo(context);
        Field[] fields = tableInfo.getFields();
        String s = new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            if (entity != null) {
                WHERE(Stream.of(fields)
                        .filter(field -> ReflectUtils.getFieldValue(entity, field) != null)
                        .map(field -> TableQueryUtils.getAssignParameter(field.getName(), Constants.ENTITY + "."))
                        .toArray(String[]::new));
            }
        }}.toString();
        return TableQueryUtils.getSqlScript(s);
    }

    /**
     * 根据条件统计条数（map 条件路径）
     */
    public String selectCount(ProviderContext context, Map<String, Object> params) {
        Map<String, Object> cm = (Map<String, Object>) params.get(Constants.COLUMNS_MAP);
        TableInfo tableInfo = getTableInfo(context);
        String s = new SQL() {{
            SELECT(tableInfo.getCountColumn());
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
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
     * 分页查询基础 SQL（不含 LIMIT/OFFSET，由分页拦截器统一追加分页与 count）
     */
    public String selectPage(ProviderContext context, final IPage page, final Map<String, Object> cm, final String... columns) {
        TableInfo tableInfo = getTableInfo(context);
        String s = new SQL() {{
            SELECT(selectColumn(tableInfo, columns));
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
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
     * 查询列白名单校验：columns 必须命中实体字段，防止列名拼接注入
     */
    private String[] selectColumn(final TableInfo tableInfo, final String... columns) {
        if (columns.length == 0) {
            return tableInfo.getSelectColumns();
        }
        for (String column : columns) {
            if (!tableInfo.hasWhereColumn(column)) {
                throw new SXException("查询字段[" + column + "]不存在于实体[" + tableInfo.getTableName() + "]");
            }
        }
        return Stream.of(ArrayUtils.add(columns, tableInfo.getPrimaryColumn()))
                .map(TableQueryUtils::getSelectColumn).toArray(String[]::new);
    }
}
