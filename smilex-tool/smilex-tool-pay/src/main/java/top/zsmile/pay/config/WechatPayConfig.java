package top.zsmile.pay.config;

import top.zsmile.pay.properties.WechatPayProperties;
import top.zsmile.pay.properties.WechatPayV3Properties;
import top.zsmile.pay.service.IWechatStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableConfigurationProperties(WechatPayProperties.class)
public class WechatPayConfig {

    private static final Logger log = LoggerFactory.getLogger(WechatPayConfig.class);

    @Resource
    private WechatPayProperties wechatPayProperties;

    @Resource
    private IWechatStorageService wechatStorageService;

    @PostConstruct
    public void init() {
        List<WechatPayV3Properties> configs = wechatPayProperties.getConfigs();
        for (WechatPayV3Properties config : configs) {
            wechatStorageService.register(config);
        }
    }
}
