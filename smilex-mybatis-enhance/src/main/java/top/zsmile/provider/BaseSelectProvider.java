package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

public class BaseSelectProvider extends BaseProvider {

    /**
     * 根据主键ID查询数据
     *
     * @param context
     * @return
     */
    public String selectById(ProviderContext context) {
        System.out.println(context);
        return new SQL() {{

        }}.toString();
    }
}
