package top.zsmile.tool.wechat.mp.service.impl;

import top.zsmile.common.redis.utils.RedisCache;
import top.zsmile.tool.wechat.mp.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mp.bean.WechatJsapiTicketRes;
import top.zsmile.tool.wechat.mp.bean.WechatMpQrcodeRes;
import top.zsmile.tool.wechat.mp.bean.WechatNotifyParams;
import top.zsmile.tool.wechat.mp.constant.WechatRedisConstant;
import top.zsmile.tool.wechat.mp.properties.WechatMpProperties;
import top.zsmile.tool.wechat.mp.service.AbstractWechatStorageService;
import top.zsmile.tool.wechat.mp.service.IWechatStorageService;
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
public class WechatRedisStorageServiceServiceImpl extends AbstractWechatStorageService implements IWechatStorageService {

    @Resource
    private RedisCache redisCache;

    @Override
    public String getDefaultAppId() {
        return redisCache.getCacheObject(WechatRedisConstant.MP_DEFAULT_KEY);
    }

    @Override
    public void setDefaultAppId(String appId) {
        redisCache.setCacheObject(WechatRedisConstant.MP_DEFAULT_KEY, appId);
    }

    @Override
    public WechatMpProperties getDefaultWechatMp() {
        String appId = redisCache.getCacheObject(WechatRedisConstant.MP_DEFAULT_KEY).toString();
        return getWechatMp(appId);
    }

    @Override
    public WechatMpProperties getWechatMp(String appId) {
        return redisCache.getCacheMapValue(WechatRedisConstant.MP_MAP, appId);
    }

    @Override
    public Map<String, WechatMpProperties> getWechatMpMap() {
        return redisCache.getCacheMap(WechatRedisConstant.MP_MAP);
    }

    @Override
    public boolean checkWechatMp(String appId) {
        Object value = getWechatMp(appId);
        return value != null;
    }

    @Override
    public void putWechatMp(WechatMpProperties wechatProperties) {
        redisCache.setCacheMapValue(WechatRedisConstant.MP_MAP, wechatProperties.getAppId(), wechatProperties);
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
        redisCache.setCacheMapValue(WechatRedisConstant.MP_AT_MAP, appId, wechatATokenRes);
    }

    @Override
    public void putJsapiTicket(String appId, WechatJsapiTicketRes jsapiTicketProperties) {
        redisCache.setCacheMapValue(WechatRedisConstant.MP_JSAPI_MAP, appId, jsapiTicketProperties);
    }

    @Override
    public WechatATokenRes getAccessToken(String appId) {
        return redisCache.getCacheMapValue(WechatRedisConstant.MP_AT_MAP, appId);
    }

    @Override
    public Map<String, WechatATokenRes> getAccessTokenMap() {
        return redisCache.getCacheMap(WechatRedisConstant.MP_AT_MAP);
    }

    @Override
    public WechatJsapiTicketRes getJsapiTicket(String appId) {
        return redisCache.getCacheMapValue(WechatRedisConstant.MP_JSAPI_MAP, appId);
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
        return redisCache.setNXCacheObject(WechatRedisConstant.MP_REPEAT + params.getSignature(), params.getSignature(), 3, TimeUnit.SECONDS);
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
        redisCache.setCacheObject(WechatRedisConstant.MP_QRCODE + qrcode.getTicket(), qrcode, expireIn, TimeUnit.SECONDS);
    }

    @Override
    public WechatMpQrcodeRes getQrStatus(String searchId) {
        if (StringUtils.isBlank(searchId)) {
            throw new IllegalArgumentException("二维码查询参数为空");
        }
        Object cacheObject = redisCache.getCacheObject(WechatRedisConstant.MP_QRCODE + searchId);
        if (cacheObject == null) {
            throw new IllegalArgumentException(String.format("未找到对应searchId=[%s]的配置，请核实！", searchId));
        }
        return (WechatMpQrcodeRes) cacheObject;
    }


}
