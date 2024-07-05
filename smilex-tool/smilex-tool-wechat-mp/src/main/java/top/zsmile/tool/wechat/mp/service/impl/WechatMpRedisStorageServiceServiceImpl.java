package top.zsmile.tool.wechat.mp.service.impl;

import top.zsmile.common.redis.utils.RedisCache;
import top.zsmile.tool.wechat.mp.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mp.bean.WechatJsapiTicketRes;
import top.zsmile.tool.wechat.mp.bean.WechatMpQrcodeRes;
import top.zsmile.tool.wechat.mp.bean.WechatNotifyParams;
import top.zsmile.tool.wechat.mp.constant.WechatMpRedisConstant;
import top.zsmile.tool.wechat.mp.properties.WechatMpProperties;
import top.zsmile.tool.wechat.mp.service.AbstractWechatStorageService;
import top.zsmile.tool.wechat.mp.service.IWechatMpStorageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 公众号开发存储在Redis
 * <p>
 * implements IWechatStorageService
 */
@Service
@ConditionalOnProperty(name = "wechat.mp.useRedis", havingValue = "true")
public class WechatMpRedisStorageServiceServiceImpl extends AbstractWechatStorageService implements IWechatMpStorageService {

    @Resource
    private RedisCache redisCache;

    @Override
    public String getDefaultAppId() {
        return redisCache.getCacheObject(WechatMpRedisConstant.MP_DEFAULT_KEY);
    }

    @Override
    public void setDefaultAppId(String appId) {
        redisCache.setCacheObject(WechatMpRedisConstant.MP_DEFAULT_KEY, appId);
    }

    @Override
    public WechatMpProperties getDefaultWechatMp() {
        String appId = redisCache.getCacheObject(WechatMpRedisConstant.MP_DEFAULT_KEY).toString();
        return getWechatMp(appId);
    }

    @Override
    public WechatMpProperties getWechatMp(String appId) {
        return redisCache.getCacheMapValue(WechatMpRedisConstant.MP_MAP, appId);
    }

    @Override
    public Map<String, WechatMpProperties> getWechatMpMap() {
        return redisCache.getCacheMap(WechatMpRedisConstant.MP_MAP);
    }

    @Override
    public boolean checkWechatMp(String appId) {
        Object value = getWechatMp(appId);
        return value != null;
    }

    @Override
    public void clearWechatMp() {
        redisCache.deleteObject(WechatMpRedisConstant.MP_MAP);
    }

    @Override
    public void putWechatMp(WechatMpProperties wechatProperties) {
        redisCache.setCacheMapValue(WechatMpRedisConstant.MP_MAP, wechatProperties.getAppId(), wechatProperties);
    }

    @Override
    public WechatMpProperties getWechatMpNotNull(String appId) {
        WechatMpProperties value = getWechatMp(appId);
        if (value == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }
        return value;
    }


    @Override
    public void putAccessToken(String appId, WechatATokenRes wechatATokenRes) {
        redisCache.setCacheMapValue(WechatMpRedisConstant.MP_AT_MAP, appId, wechatATokenRes);
    }

    @Override
    public void putJsapiTicket(String appId, WechatJsapiTicketRes jsapiTicketProperties) {
        redisCache.setCacheMapValue(WechatMpRedisConstant.MP_JSAPI_MAP, appId, jsapiTicketProperties);
    }

    @Override
    public WechatATokenRes getAccessToken(String appId) {
        return redisCache.getCacheMapValue(WechatMpRedisConstant.MP_AT_MAP, appId);
    }

    @Override
    public Map<String, WechatATokenRes> getAccessTokenMap() {
        return redisCache.getCacheMap(WechatMpRedisConstant.MP_AT_MAP);
    }

    @Override
    public WechatJsapiTicketRes getJsapiTicket(String appId) {
        return redisCache.getCacheMapValue(WechatMpRedisConstant.MP_JSAPI_MAP, appId);
    }

    @Override
    public WechatJsapiTicketRes getJsapiTicketNotNull(String appId) {
        WechatJsapiTicketRes value = getJsapiTicket(appId);
        if (value == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }
        return value;
    }

    @Override
    public boolean setNXRepeat(WechatNotifyParams params) {
        return redisCache.setNXCacheObject(WechatMpRedisConstant.MP_REPEAT + params.getSignature(), params.getSignature(), 3, TimeUnit.SECONDS);
    }

    @Override
    public void setQrStatus(WechatMpQrcodeRes qrcode, Integer status) {
        setQrStatus(qrcode, status, qrcode.getExpireIn());
    }

    @Override
    public void setQrStatus(WechatMpQrcodeRes qrcode, Integer status, Integer expireIn) {
        if (qrcode == null) {
            throw new IllegalArgumentException("二维码不能为空");
        }
        qrcode.setStatus(status);
        redisCache.setCacheObject(WechatMpRedisConstant.MP_QRCODE + qrcode.getTicket(), qrcode, expireIn, TimeUnit.SECONDS);
    }

    @Override
    public WechatMpQrcodeRes getQrStatus(String searchId) {
        if (StringUtils.isBlank(searchId)) {
            throw new IllegalArgumentException("二维码查询参数为空");
        }
        Object cacheObject = redisCache.getCacheObject(WechatMpRedisConstant.MP_QRCODE + searchId);
        if (cacheObject == null) {
            throw new IllegalArgumentException(String.format("未找到对应searchId=[%s]的配置，请核实！", searchId));
        }
        return (WechatMpQrcodeRes) cacheObject;
    }


}
