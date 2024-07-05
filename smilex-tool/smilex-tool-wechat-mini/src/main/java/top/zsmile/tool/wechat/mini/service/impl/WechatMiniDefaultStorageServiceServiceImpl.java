package top.zsmile.tool.wechat.mini.service.impl;

import top.zsmile.tool.wechat.mini.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mini.properties.WechatMiniProperties;
import top.zsmile.tool.wechat.mini.service.AbstractWechatMiniStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公众号开发存储在本地
 */
@Service
@ConditionalOnProperty(name = "wechat.mini.useRedis", havingValue = "false")
public class WechatMiniDefaultStorageServiceServiceImpl extends AbstractWechatMiniStorageService {

    private static final ConcurrentHashMap<String, ReentrantLock> LOCKS = new ConcurrentHashMap<>();

//    @Resource(name = "caffeineRocksMiniCache")
//    private CaffeineRocksCache cache;

    private static String defaultAppId = null;

    @Override
    public String getDefaultAppId() {
        return defaultAppId;
    }

    @Override
    public void setDefaultAppId(String appId) {
        defaultAppId = appId;
    }

    @Override
    public WechatMiniProperties getDefaultWechatMp() {
        return getWechatMp(getDefaultAppId());
    }

//    @Override
//    public WechatMiniProperties getWechatMp(String appId) {
//        return (WechatMiniProperties) cache.get(WechatRedisConstant.MINI_MAP + appId);
//    }
//
//    @Override
//    public Map<String, WechatMiniProperties> getWechatMpMap() {
//        return cache.getByPrefix(WechatRedisConstant.MINI_MAP).collect(Collectors.toMap(Map.Entry::getKey, t -> (WechatMiniProperties) t.getValue()));
//    }
//
//    @Override
//    public WechatMiniProperties getWechatMpNotNull(String appId) {
//        WechatMiniProperties wechatMp = getWechatMp(appId);
//        if (wechatMp == null) {
//            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
//        }
//        return wechatMp;
//    }
//
//    @Override
//    public boolean checkWechatMp(String appId) {
//        WechatMiniProperties wechatMp = getWechatMp(appId);
//        return wechatMp != null;
//    }
//
//    @Override
//    public void clearWechatMp() {
//        cache.clear(WechatRedisConstant.MINI_MAP);
//    }
//
//    @Override
//    public void putWechatMp(WechatMiniProperties wechatProperties) {
//        cache.set(WechatRedisConstant.MINI_MAP + wechatProperties.getAppId(), wechatProperties);
//    }
//
//    @Override
//    public void putAccessToken(String appId, WechatATokenRes wechatATokenRes) {
//        cache.set(WechatRedisConstant.MINI_AT_MAP + appId, wechatATokenRes);
//    }
//
//
//    @Override
//    public WechatATokenRes getAccessToken(String appId) {
//        return (WechatATokenRes) cache.get(WechatRedisConstant.MINI_AT_MAP + appId);
//    }
//
//
//    @Override
//    public Map<String, WechatATokenRes> getAccessTokenMap() {
//        return cache.getByPrefix(WechatRedisConstant.MINI_AT_MAP).collect(Collectors.toMap(Map.Entry::getKey, t -> (WechatATokenRes) t.getValue()));
//    }


    @Override
    public WechatMiniProperties getWechatMp(String appId) {
        return null;
    }

    @Override
    public Map<String, WechatMiniProperties> getWechatMpMap() {
        return null;
    }

    @Override
    public WechatMiniProperties getWechatMpNotNull(String appId) {
        return null;
    }

    @Override
    public boolean checkWechatMp(String appId) {
        return false;
    }

    @Override
    public void clearWechatMp() {

    }

    @Override
    public void putWechatMp(WechatMiniProperties wechatProperties) {

    }

    @Override
    public void putAccessToken(String appId, WechatATokenRes wechatATokenRes) {

    }

    @Override
    public WechatATokenRes getAccessToken(String appId) {
        return null;
    }

    @Override
    public Map<String, WechatATokenRes> getAccessTokenMap() {
        return null;
    }
}
