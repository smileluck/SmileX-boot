package top.zsmile.tool.wechat.mini.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@ConfigurationProperties(prefix = "wechat.mini")
public class WechatProperties {
    private boolean useRedis = false;

    /**
     * 默认公众号AppId
     */
    private String defaultAppId;

    private List<WechatMiniProperties> configs;

    public boolean isUseRedis() {
        return useRedis;
    }

    public void setUseRedis(boolean useRedis) {
        this.useRedis = useRedis;
    }

    public List<WechatMiniProperties> getConfigs() {
        return configs;
    }

    public void setConfigs(List<WechatMiniProperties> configs) {
        this.configs = configs;
    }

    public String getDefaultAppId() {
        return defaultAppId;
    }

    public void setDefaultAppId(String defaultAppId) {
        this.defaultAppId = defaultAppId;
    }
}
