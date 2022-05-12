package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.meta.TableInfo;

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
}
