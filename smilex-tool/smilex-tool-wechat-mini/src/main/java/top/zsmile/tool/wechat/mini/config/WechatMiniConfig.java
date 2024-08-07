package top.zsmile.tool.wechat.mini.config;


import org.springframework.util.CollectionUtils;
import top.zsmile.tool.wechat.mini.properties.WechatMiniProperties;
import top.zsmile.tool.wechat.mini.properties.WechatProperties;
import top.zsmile.tool.wechat.mini.service.AbstractWechatMiniStorageService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 微信公众号配置
 */
@Configuration
@EnableConfigurationProperties(WechatProperties.class)
public class WechatMiniConfig {

    @Value("${wechat.mini.useRedis}")
    private Boolean userRedis;

    @Resource
    private WechatProperties wechatProperties;
    @Autowired
    private AbstractWechatMiniStorageService wechatStorageService;

    @PostConstruct
//    @Primary
    public AbstractWechatMiniStorageService abstractWechatStorageService() {
//        AbstractWechatMiniStorageService wechatStorageService = null;
//        if (wechatProperties.isUseRedis()) {
//            wechatStorageService = wechatRedisStorage;
//        } else {
//            wechatStorageService = new WechatDefaultStorageServiceServiceImpl();
//        }
        boolean isExist = false;
        if (StringUtils.isNotBlank(wechatProperties.getDefaultAppId())) {
            wechatStorageService.setDefaultAppId(wechatProperties.getDefaultAppId());
        }

        List<WechatMiniProperties> configs = wechatProperties.getConfigs();
        if (CollectionUtils.isEmpty(configs)) {
            throw new RuntimeException("未添加下公众号相关配置！");
        }
        wechatStorageService.clearWechatMp();
        for (int i = 0; i < configs.size(); i++) {
            WechatMiniProperties wechatMpProperties = configs.get(i);
            wechatStorageService.putWechatMp(wechatMpProperties);
            if (i == 0 && StringUtils.isBlank(wechatProperties.getDefaultAppId())) {
                wechatStorageService.setDefaultAppId(wechatMpProperties.getAppId());
                isExist = true;
            } else if (!isExist && wechatStorageService.getDefaultWechatMp().equals(wechatMpProperties.getAppId())) {
                isExist = true;
            }
        }

        if (!isExist) {
            throw new RuntimeException("未添加下默认公众号信息不存在！");
        }

        return wechatStorageService;
    }

}
