package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

public class BaseInsertProvider extends BaseProvider {

    public String insert(ProviderContext context) {
        System.out.println(context);
        return new SQL() {{

        }}.toString();
    }
}
