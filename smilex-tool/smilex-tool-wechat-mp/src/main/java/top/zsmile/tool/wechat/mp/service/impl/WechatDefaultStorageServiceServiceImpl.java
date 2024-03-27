package top.zsmile.tool.wechat.mp.service.impl;

import top.zsmile.common.core.utils.LockUtils;
import top.zsmile.tool.wechat.mp.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mp.bean.WechatJsapiTicketRes;
import top.zsmile.tool.wechat.mp.bean.WechatMpQrcodeRes;
import top.zsmile.tool.wechat.mp.bean.WechatNotifyParams;
import top.zsmile.tool.wechat.mp.constant.WechatRedisConstant;
import top.zsmile.tool.wechat.mp.properties.WechatMpProperties;
import top.zsmile.tool.wechat.mp.service.AbstractWechatStorageService;
import top.zsmile.tool.wechat.mp.utils.CaffeineRocksCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 公众号开发存储在本地
 */
@Service
@ConditionalOnProperty(name = "wechat.mp.useRedis", havingValue = "false")
public class WechatDefaultStorageServiceServiceImpl extends AbstractWechatStorageService {

    private static final ConcurrentHashMap<String, ReentrantLock> LOCKS = new ConcurrentHashMap<>();

    @Resource
    private CaffeineRocksCache cache;

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
    public WechatMpProperties getDefaultWechatMp() {
        return getWechatMp(getDefaultAppId());
    }

    @Override
    public WechatMpProperties getWechatMp(String appId) {
        return (WechatMpProperties) cache.get(WechatRedisConstant.MP_MAP + appId);
    }

    @Override
    public Map<String, WechatMpProperties> getWechatMpMap() {
        return cache.getByPrefix(WechatRedisConstant.MP_MAP).collect(Collectors.toMap(Map.Entry::getKey, t -> (WechatMpProperties) t.getValue()));
    }

    @Override
    public WechatMpProperties getWechatMpNotNull(String appId) {
        WechatMpProperties wechatMp = getWechatMp(appId);
        if (wechatMp == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }
        return wechatMp;
    }

    @Override
    public boolean checkWechatMp(String appId) {
        WechatMpProperties wechatMp = getWechatMp(appId);
        return wechatMp != null;
    }

    @Override
    public void putWechatMp(WechatMpProperties wechatProperties) {
        cache.set(WechatRedisConstant.MP_MAP + wechatProperties.getAppId(), wechatProperties);
    }

    @Override
    public void putAccessToken(String appId, WechatATokenRes wechatATokenRes) {
        cache.set(WechatRedisConstant.MP_AT_MAP + appId, wechatATokenRes);
    }

    @Override
    public WechatATokenRes getAccessToken(String appId) {
        return (WechatATokenRes) cache.get(WechatRedisConstant.MP_AT_MAP + appId);
    }


    @Override
    public Map<String, WechatATokenRes> getAccessTokenMap() {
        return cache.getByPrefix(WechatRedisConstant.MP_AT_MAP).collect(Collectors.toMap(Map.Entry::getKey, t -> (WechatATokenRes) t.getValue()));
    }

    @Override
    public WechatJsapiTicketRes getJsapiTicket(String appId) {
        return (WechatJsapiTicketRes) cache.get(WechatRedisConstant.MP_JSAPI_MAP + appId);
    }

    @Override
    public void putJsapiTicket(String appId, WechatJsapiTicketRes jsapiTicketProperties) {
        cache.set(WechatRedisConstant.MP_JSAPI_MAP + appId, jsapiTicketProperties);
    }

    @Override
    public WechatJsapiTicketRes getJsapiTicketNotNull(String appId) {
        WechatJsapiTicketRes jsapiTicket = getJsapiTicket(appId);
        if (jsapiTicket == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的JsapiTicket，请核实！", appId));
        }
        return jsapiTicket;
    }

    @Override
    public boolean setNXRepeat(WechatNotifyParams params) {
        return LockUtils.tryLock(WechatRedisConstant.MP_REPEAT + params.getSignature(), 3, TimeUnit.SECONDS);
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
        cache.set(WechatRedisConstant.MP_QRCODE + qrcode.getTicket(), qrcode);
    }

    @Override
    public WechatMpQrcodeRes getQrStatus(String searchId) {
        if (StringUtils.isBlank(searchId)) {
            throw new IllegalArgumentException("二维码查询参数为空");
        }
        Object cacheObject = cache.get(WechatRedisConstant.MP_QRCODE + searchId);
        if (cacheObject == null) {
            throw new IllegalArgumentException(String.format("未找到对应searchId=[%s]的配置，请核实！", searchId));
        }
        return (WechatMpQrcodeRes) cacheObject;
    }


}
