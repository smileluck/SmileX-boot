package top.zsmile.tool.wechat.mini.service.impl;

import top.zsmile.common.redis.utils.RedisCache;
import top.zsmile.tool.wechat.mini.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mini.constant.WechatMiniRedisConstant;
import top.zsmile.tool.wechat.mini.properties.WechatMiniProperties;
import top.zsmile.tool.wechat.mini.service.AbstractWechatMiniStorageService;
import top.zsmile.tool.wechat.mini.service.IWechatMiniStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 公众号开发存储在Redis
 * <p>
 * implements IWechatStorageService
 */
@Service
@ConditionalOnProperty(name = "wechat.mini.useRedis", havingValue = "true")
public class WechatMiniRedisStorageServiceServiceImpl extends AbstractWechatMiniStorageService implements IWechatMiniStorageService {

    @Resource
    private RedisCache redisCache;

    @Override
    public String getDefaultAppId() {
        return redisCache.getCacheObject(WechatMiniRedisConstant.MINI_DEFAULT_KEY);
    }

    @Override
    public void setDefaultAppId(String appId) {
        redisCache.setCacheObject(WechatMiniRedisConstant.MINI_DEFAULT_KEY, appId);
    }

    @Override
    public WechatMiniProperties getDefaultWechatMp() {
        String appId = redisCache.getCacheObject(WechatMiniRedisConstant.MINI_DEFAULT_KEY).toString();
        return getWechatMp(appId);
    }

    @Override
    public WechatMiniProperties getWechatMp(String appId) {
        return redisCache.getCacheMapValue(WechatMiniRedisConstant.MINI_MAP, appId);
    }

    @Override
    public Map<String, WechatMiniProperties> getWechatMpMap() {
        return redisCache.getCacheMap(WechatMiniRedisConstant.MINI_MAP);
    }

    @Override
    public boolean checkWechatMp(String appId) {
        Object value = getWechatMp(appId);
        return value != null;
    }

    @Override
    public void clearWechatMp() {
        redisCache.deleteObject(WechatMiniRedisConstant.MINI_MAP);
    }

    @Override
    public void putWechatMp(WechatMiniProperties wechatProperties) {
        redisCache.setCacheMapValue(WechatMiniRedisConstant.MINI_MAP, wechatProperties.getAppId(), wechatProperties);
    }

    @Override
    public void putAccessToken(String appId, WechatATokenRes wechatATokenRes) {
        redisCache.setCacheMapValue(WechatMiniRedisConstant.MINI_AT_MAP, appId, wechatATokenRes);
    }

    @Override
    public WechatMiniProperties getWechatMpNotNull(String appId) {
        WechatMiniProperties value = getWechatMp(appId);
        if (value == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }
        return value;
    }


    @Override
    public WechatATokenRes getAccessToken(String appId) {
        return redisCache.getCacheMapValue(WechatMiniRedisConstant.MINI_AT_MAP, appId);
    }

    @Override
    public Map<String, WechatATokenRes> getAccessTokenMap() {
        return redisCache.getCacheMap(WechatMiniRedisConstant.MINI_AT_MAP);
    }

}
