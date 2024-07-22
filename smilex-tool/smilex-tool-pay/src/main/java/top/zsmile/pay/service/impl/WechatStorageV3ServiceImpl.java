package top.zsmile.pay.service.impl;

import top.zsmile.common.core.utils.Asserts;
import top.zsmile.common.redis.utils.RedisCache;
import top.zsmile.pay.bean.WxV3Storage;
import top.zsmile.pay.constant.TradeCacheConstant;
import top.zsmile.pay.properties.WechatPayV3Properties;
import top.zsmile.pay.service.IWechatStorageService;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class WechatStorageV3ServiceImpl implements IWechatStorageService {

    @Resource
    private RedisCache redisCache;

    /**
     * 配置信息MAP
     */
    private static final ConcurrentHashMap<String, WechatPayV3Properties> PROPERTIES_MAP = new ConcurrentHashMap<>();

    /**
     * 配置MAP
     */
    private static final ConcurrentHashMap<String, WxV3Storage> CONFIG_MAP = new ConcurrentHashMap<>();

    @Override
    public void save(WechatPayV3Properties wechatPayV3Properties) {
        PROPERTIES_MAP.put(wechatPayV3Properties.getId(), wechatPayV3Properties);
    }

    @Override
    public WechatPayV3Properties get(String id) {
        Asserts.isNotBlank(id, "配置Id不能为空");
        return PROPERTIES_MAP.get(id);
    }

    @Override
    public void register(WechatPayV3Properties wechatPayV3Properties) {
        save(wechatPayV3Properties);
        RSAAutoCertificateConfig config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(wechatPayV3Properties.getMchid())
                        .privateKeyFromPath(wechatPayV3Properties.getPrivateKeyPath())
                        .merchantSerialNumber(wechatPayV3Properties.getApiSerialNum())
                        .apiV3Key(wechatPayV3Properties.getApiKey())
                        .build();
        WxV3Storage wxV3Storage = new WxV3Storage(wechatPayV3Properties.getAppid(), wechatPayV3Properties.getMchid(), wechatPayV3Properties.getNotifyUrl(), wechatPayV3Properties.getPrivateKeyPath(), config);
        CONFIG_MAP.put(wechatPayV3Properties.getId(), wxV3Storage);
    }

    @Override
    public WxV3Storage getConfig(String id) {
        return CONFIG_MAP.get(id);
    }

    @Override
    public boolean repeatNotify(String id) {
        return redisCache.setNXCacheObject(TradeCacheConstant.REPEAT + id, 1, 3, TimeUnit.SECONDS);
    }

    @Override
    public void saveTransactionStatus(String id, String statue) {
        redisCache.setCacheObject(TradeCacheConstant.TRADE_STATUS + id, statue, 5, TimeUnit.MINUTES);
    }

    @Override
    public String getTransactionStatus(String id) {
        return redisCache.getCacheObject(TradeCacheConstant.TRADE_STATUS + id);
    }

    @Override
    public String getByAppId(String appid) {
        for (Map.Entry<String, WechatPayV3Properties> entry : PROPERTIES_MAP.entrySet()
        ) {
            WechatPayV3Properties properties = entry.getValue();
            if (appid.equals(properties.getAppid())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
