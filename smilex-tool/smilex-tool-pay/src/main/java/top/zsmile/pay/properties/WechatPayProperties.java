package top.zsmile.pay.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "wechat.pay.v3")
public class WechatPayProperties {

    /**
     * 配置列表
     */
    private List<WechatPayV3Properties> configs;

    public List<WechatPayV3Properties> getConfigs() {
        return configs;
    }

    public void setConfigs(List<WechatPayV3Properties> configs) {
        this.configs = configs;
    }
}
