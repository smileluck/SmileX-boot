package top.zsmile.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.zsmile.meta.TableInfo;
import top.zsmile.utils.Constants;
import top.zsmile.utils.TableQueryUtils;

import java.util.Map;

public class BaseDeleteProvider extends BaseProvider {

    /**
     * 逻辑删除
     *
     * @param context
     * @return
     */
    public String deleteLogicById(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        return new SQL() {{
            UPDATE(tableInfo.getTableName());
            SET(tableInfo.logicDelColumnSet());
            WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
    }

    /**
     * 批量逻辑删除
     *
     * @param context
     * @return
     */
    public String deleteLogicBatchIds(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        String sql = new SQL() {{
            UPDATE(tableInfo.getTableName());
            SET(tableInfo.logicDelColumnSet());
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
    public String deleteLogicByMap(ProviderContext context, Map<String, Object> cm) {
        TableInfo tableInfo = getTableInfo(context);
        String sql = new SQL() {{
            UPDATE(tableInfo.getTableName());
            SET(tableInfo.logicDelColumnSet());
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
    public String deletePhysicsByMap(ProviderContext context, Map<String, Object> cm) {
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
