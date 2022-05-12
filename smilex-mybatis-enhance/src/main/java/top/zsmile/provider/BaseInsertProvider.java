package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.meta.TableInfo;

public class BaseInsertProvider extends BaseProvider {

    public String insert(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        return new SQL() {{
            INSERT_INTO(tableInfo.getTableName());
            INTO_COLUMNS(tableInfo.getColumns());
            INTO_VALUES(tableInfo.getInjectParameter());
        }}.toString();
    }

    
}
