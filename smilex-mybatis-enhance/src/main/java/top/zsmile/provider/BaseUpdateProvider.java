package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.meta.TableInfo;

public class BaseUpdateProvider extends BaseProvider {

    /**
     * 根据主键ID更新数据
     *
     * @param context
     * @return
     */
    public String updateById(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        return new SQL() {{
            UPDATE(tableInfo.getTableName());
            WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
    }
}
