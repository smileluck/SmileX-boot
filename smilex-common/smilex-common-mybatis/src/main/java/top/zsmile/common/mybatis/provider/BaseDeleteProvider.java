package top.zsmile.common.mybatis.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;
import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.meta.conditions.udpate.UpdateWrapper;
import top.zsmile.common.mybatis.utils.Constants;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

import java.util.Map;

public class BaseDeleteProvider extends BaseProvider {

    /**
     * 根据Wrapper删除， 优先逻辑删除，如果没有逻辑删除字段则物理删除
     *
     * @param context
     * @param wrapper
     * @return
     */
    public String delete(ProviderContext context, @Param(StringPool.WRAPPER) UpdateWrapper wrapper) {
        TableInfo tableInfo = getTableInfo(context);

        String whereSqlFragment = wrapper.getWhereSqlFragment();
        String sql;
        if (tableInfo.hasLogicDelColumn()) {
            String sqlSet = wrapper.getSqlSet();
            sql = new SQL() {{
                UPDATE(tableInfo.getTableName());
                SET(tableInfo.logicDelColumnSet());
                if (StringUtils.isNotBlank(sqlSet)) SET(sqlSet);
                if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
                if (StringUtils.isNotBlank(whereSqlFragment)) WHERE(whereSqlFragment);
            }}.toString();
        } else {
            sql = new SQL() {{
                DELETE_FROM(tableInfo.getTableName());
                if (StringUtils.isNotBlank(whereSqlFragment)) WHERE(whereSqlFragment);
            }}.toString();
        }
        return sql;
    }

    /**
     * 删除，优先逻辑删除，如果没有逻辑删除字段则物理删除
     *
     * @param context
     * @return
     */
    public String deleteById(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        if (tableInfo.hasLogicDelColumn()) {
            return new SQL() {{
                UPDATE(tableInfo.getTableName());
                SET(tableInfo.logicDelColumnSet());
                WHERE(tableInfo.primaryColumnWhere());
            }}.toString();
        } else {
            return new SQL() {{
                DELETE_FROM(tableInfo.getTableName()).WHERE(tableInfo.primaryColumnWhere());
            }}.toString();
        }
    }


    /**
     * 批量逻辑删除
     *
     * @param context
     * @return
     */
    public String deleteBatchIds(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        String sql;
        if (tableInfo.hasLogicDelColumn()) {
            sql = new SQL() {{
                UPDATE(tableInfo.getTableName());
                SET(tableInfo.logicDelColumnSet());
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
     * 根据 集合字段 逻辑删除
     *
     * @param context
     * @return
     */
    public String deleteByMap(ProviderContext context, Map<String, Object> params) {
        Map<String, Object> cm = (Map<String, Object>) params.get(Constants.COLUMNS_MAP);
        TableInfo tableInfo = getTableInfo(context);
        String sql;
        if (tableInfo.hasLogicDelColumn()) {
            sql = new SQL() {{
                UPDATE(tableInfo.getTableName());
                SET(tableInfo.logicDelColumnSet());
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
     * 物理删除
     *
     * @param context
     * @return
     */
    public String deletePhysicsById(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        return new SQL() {{
            DELETE_FROM(tableInfo.getTableName()).WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
    }

    /**
     * 批量物理删除
     *
     * @param context
     * @return
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
     * 根据 集合字段 逻辑删除
     *
     * @param context
     * @return
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
}
