package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.meta.TableInfo;

public class BaseSelectProvider extends BaseProvider {

    /**
     * 根据主键ID查询数据
     *
     * @param context
     * @return
     */
    public String selectById(Object id, ProviderContext context) {
        System.out.println(context);
        TableInfo tableInfo = getTableInfo(context);

        String s = new SQL() {{
            SELECT(tableInfo.getSelectColumns());
            FROM(tableInfo.getTableName());
            WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
        System.out.println(s);
        return s;
    }
}
