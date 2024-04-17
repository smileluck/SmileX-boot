package top.zsmile.pay.config;

import top.zsmile.pay.properties.AliPayConfigProperties;
import top.zsmile.pay.properties.AliPayProperties;
import top.zsmile.pay.service.IAliStorageService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableConfigurationProperties(AliPayProperties.class)
public class AliPayConfig {

    @Resource
    private AliPayProperties aliPayProperties;

    @Resource
    private IAliStorageService aliStorageService;

    @PostConstruct
    public void init() {
        List<AliPayConfigProperties> configs = aliPayProperties.getConfigs();
        for (AliPayConfigProperties config : configs) {
            aliStorageService.save(config);
        }
    }
}
