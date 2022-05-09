package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

public class BaseUpdateProvider extends BaseProvider {

    /**
     * 根据主键ID更新数据
     *
     * @param context
     * @return
     */
    public String updateById(ProviderContext context) {
        System.out.println(context);
        return new SQL() {{

        }}.toString();
    }
}
