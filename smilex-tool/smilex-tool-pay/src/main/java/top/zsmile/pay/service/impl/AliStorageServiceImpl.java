package top.zsmile.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import top.zsmile.common.redis.utils.RedisCache;
import top.zsmile.pay.bean.AliStorage;
import top.zsmile.pay.constant.TradeCacheConstant;
import top.zsmile.pay.properties.AliPayConfigProperties;
import top.zsmile.pay.service.IAliStorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 支付宝存储服务
 */
@Service
public class AliStorageServiceImpl implements IAliStorageService {

    @Resource
    private RedisCache redisCache;

    /**
     * 配置信息MAP
     */
    private static final ConcurrentHashMap<String, AliStorage> CLIENT_MAP = new ConcurrentHashMap<>();

    @Override
    public void save(AliPayConfigProperties properties) {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(properties.getServeUrl());
        alipayConfig.setAppId(properties.getAppid());
        alipayConfig.setPrivateKey(properties.getPrivateKey());
        alipayConfig.setFormat(properties.getFormat());
        alipayConfig.setAlipayPublicKey(properties.getAlipayPublicKey());
        alipayConfig.setCharset(properties.getCharset());
        alipayConfig.setSignType(properties.getSignType());
        try {
            AliStorage aliStorage = new AliStorage();
            aliStorage.setProperties(properties);
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
            aliStorage.setClient(alipayClient);
            CLIENT_MAP.put(properties.getId(), aliStorage);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AliStorage get(String id) {
        return CLIENT_MAP.get(id);
    }


    @Override
    public boolean repeatNotify(String id) {
        return redisCache.setNXCacheObject(TradeCacheConstant.REPEAT + id, 1, 3, TimeUnit.SECONDS);
    }
}
