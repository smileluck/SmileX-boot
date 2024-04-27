package top.zsmile.pay.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "ali.pay")
public class AliPayProperties {
    /**
     * 配置列表
     */
    private List<AliPayConfigProperties> configs;

    public List<AliPayConfigProperties> getConfigs() {
        return configs;
    }

    public void setConfigs(List<AliPayConfigProperties> configs) {
        this.configs = configs;
    }
}
