package top.zsmile.construct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

public class MybatisEnhanceInit implements InitializingBean {

    // TODO 后续做成start时，可以优化成配置项。
    private String packageStr = "top.zsmile.modules.*.entity";

    @Override
    public void afterPropertiesSet() throws Exception {


    }
}
