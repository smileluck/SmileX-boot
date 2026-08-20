package top.zsmile.common.mybatis.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.meta.conditions.AbstractUpdateWrapper;
import top.zsmile.common.mybatis.utils.Constants;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

import java.util.Map;

public class BaseDeleteProvider extends BaseProvider {

    /**
     * 根据 Wrapper 删除，优先逻辑删除，无逻辑删除字段则物理删除。
     * <p>
     * 逻辑删除附带 update_time = NOW() 记录删除时间（表存在该列时）。
     */
    public String delete(ProviderContext context, AbstractUpdateWrapper ew) {
        TableInfo tableInfo = getTableInfo(context);
        String whereSqlFragment = ew.getWhereSqlFragment();
        String sql;
        if (tableInfo.hasLogicDelColumn()) {
            String sqlSet = ew.getSqlSet();
            sql = new SQL() {{
                UPDATE(tableInfo.getTableName());
                SET(logicDelSet(tableInfo));
                if (StringUtils.isNotBlank(sqlSet)) SET(sqlSet);
                WHERE(tableInfo.logicDelColumnWhere());
                if (StringUtils.isNotBlank(whereSqlFragment)) WHERE(whereSqlFragment);
            }}.toString();
        } else {
            sql = new SQL() {{
                DELETE_FROM(tableInfo.getTableName());
                if (StringUtils.isNotBlank(whereSqlFragment)) WHERE(whereSqlFragment);
            }}.toString();
        }
        String lastSql = ew.getLastSql();
        return StringUtils.isBlank(lastSql) ? sql : sql + lastSql;
    }

    /**
     * 根据ID删除，优先逻辑删除
     */
    public String deleteById(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        if (tableInfo.hasLogicDelColumn()) {
            return new SQL() {{
                UPDATE(tableInfo.getTableName());
                SET(logicDelSet(tableInfo));
                WHERE(tableInfo.primaryColumnWhere());
            }}.toString();
        }
        return new SQL() {{
            DELETE_FROM(tableInfo.getTableName()).WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
    }

    /**
     * 批量删除，优先逻辑删除
     */
    public String deleteBatchIds(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        String sql;
        if (tableInfo.hasLogicDelColumn()) {
            sql = new SQL() {{
                UPDATE(tableInfo.getTableName());
                SET(logicDelSet(tableInfo));
                WHERE(tableInfo.getPrimaryColumn() + " in <foreach item='item' collection='coll' open='(' separator=',' close=')'>#{item}</foreach>");
            }}.toString();
        } else {
            sql = new SQL() {{
                DELETE_FROM(tableInfo.getTableName());
                WHERE(tableInfo.getPrimaryColumn() + " in <foreach item='item' collection='coll' open='(' separator=',' close=')'>#{item}</foreach>");
            }}.toString();
        }
        return TableQueryUtils.getSqlScript(sql);
    }

    /**
     * 根据 map 条件删除，优先逻辑删除
     */
    public String deleteByMap(ProviderContext context, Map<String, Object> params) {
        Map<String, Object> cm = (Map<String, Object>) params.get(Constants.COLUMNS_MAP);
        TableInfo tableInfo = getTableInfo(context);
        String sql;
        if (tableInfo.hasLogicDelColumn()) {
            sql = new SQL() {{
                UPDATE(tableInfo.getTableName());
                SET(logicDelSet(tableInfo));
                if (!CollectionUtils.isEmpty(cm)) {
                    String mapCondition = TableQueryUtils.getMapCondition(tableInfo, cm);
                    if (!StringUtils.isEmpty(mapCondition)) {
                        WHERE(mapCondition);
                    }
                }
            }}.toString();
        } else {
            sql = new SQL() {{
                DELETE_FROM(tableInfo.getTableName());
                if (!CollectionUtils.isEmpty(cm)) {
                    String mapCondition = TableQueryUtils.getMapCondition(tableInfo, cm);
                    if (!StringUtils.isEmpty(mapCondition)) {
                        WHERE(mapCondition);
                    }
                }
            }}.toString();
        }
        return TableQueryUtils.getSqlScript(sql);
    }

    /**
     * 物理删除：根据ID
     */
    public String deletePhysicsById(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        return new SQL() {{
            DELETE_FROM(tableInfo.getTableName()).WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
    }

    /**
     * 物理删除：批量
     */
    public String deletePhysicsBatchIds(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        String sql = new SQL() {{
            DELETE_FROM(tableInfo.getTableName());
            WHERE(tableInfo.getPrimaryColumn() + " in <foreach item='item' collection='coll' open='(' separator=',' close=')'>#{item}</foreach>");
        }}.toString();
        return TableQueryUtils.getSqlScript(sql);
    }

    /**
     * 物理删除：根据 map 条件
     */
    public String deletePhysicsByMap(ProviderContext context, Map<String, Object> params) {
        Map<String, Object> cm = (Map<String, Object>) params.get(Constants.COLUMNS_MAP);
        TableInfo tableInfo = getTableInfo(context);
        String sql = new SQL() {{
            DELETE_FROM(tableInfo.getTableName());
            if (!CollectionUtils.isEmpty(cm)) {
                String mapCondition = TableQueryUtils.getMapCondition(tableInfo, cm);
                if (!StringUtils.isEmpty(mapCondition)) {
                    WHERE(mapCondition);
                }
            }
        }}.toString();
        return TableQueryUtils.getSqlScript(sql);
    }

    /**
     * 逻辑删除 SET 片段：del_flag = 1, update_time = NOW()（存在审计列时记录删除时间）
     */
    private String logicDelSet(TableInfo tableInfo) {
        if (tableInfo.hasField("updateTime")) {
            return tableInfo.logicDelColumnSet() + ", " + TableQueryUtils.humpToLineName("updateTime") + " = NOW()";
        }
        return tableInfo.logicDelColumnSet();
    }
}
