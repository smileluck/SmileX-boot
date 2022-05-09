package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

public class BaseDeleteProvider extends BaseProvider {

    /**
     * 逻辑删除
     *
     * @param context
     * @return
     */
    public String deleteLogicById(ProviderContext context) {
        System.out.println(context);
        return new SQL() {{

        }}.toString();
    }

    /**
     * 物理删除
     *
     * @param context
     * @return
     */
    public String deletePhysicsById(ProviderContext context) {
        System.out.println(context);
        return new SQL() {{

        }}.toString();
    }
}
