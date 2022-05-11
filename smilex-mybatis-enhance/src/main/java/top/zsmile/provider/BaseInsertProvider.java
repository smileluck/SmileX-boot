package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.meta.TableInfo;

public class BaseInsertProvider extends BaseProvider {

    public String insert(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        System.out.println(tableInfo);
        return new SQL() {{

        }}.toString();
    }
}
