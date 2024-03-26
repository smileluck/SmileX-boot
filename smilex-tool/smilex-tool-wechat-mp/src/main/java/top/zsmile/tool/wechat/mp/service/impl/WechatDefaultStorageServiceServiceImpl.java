package top.zsmile.tool.wechat.mp.service.impl;

import top.zsmile.tool.wechat.mp.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mp.bean.WechatJsapiTicketRes;
import top.zsmile.tool.wechat.mp.bean.WechatMpQrcodeRes;
import top.zsmile.tool.wechat.mp.bean.WechatNotifyParams;
import top.zsmile.tool.wechat.mp.properties.WechatMpProperties;
import top.zsmile.tool.wechat.mp.service.AbstractWechatStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 公众号开发存储在本地
 */
@Service
@ConditionalOnProperty(name = "wechat.mp.useRedis", havingValue = "false")
public class WechatDefaultStorageServiceServiceImpl extends AbstractWechatStorageService {


    /**
     * 本地存储
     */
    private static final ConcurrentHashMap<String, WechatMpProperties> STORAGE_MAP = new ConcurrentHashMap(1);

    /**
     * 本地存储AccessToken
     */
    private static final ConcurrentHashMap<String, WechatATokenRes> ACCESSTOKEN_MAP = new ConcurrentHashMap(1);

    /**
     * 本地存储JSAPITICKET_MAP
     */
    private static final ConcurrentHashMap<String, WechatJsapiTicketRes> JSAPITICKET_MAP = new ConcurrentHashMap(1);

    /**
     * 本地存储QRCode_MAP
     */
    private static final ConcurrentHashMap<String, Integer> QRCODE_MAP = new ConcurrentHashMap();


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
        return STORAGE_MAP.get(getDefaultAppId());
    }

    @Override
    public WechatMpProperties getWechatMp(String appId) {
        return STORAGE_MAP.get(appId);
    }

    @Override
    public Map<String, WechatMpProperties> getWechatMpMap() {
        return Collections.unmodifiableMap(STORAGE_MAP);
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
        return STORAGE_MAP.containsKey(appId);
    }

    @Override
    public void putWechatMp(WechatMpProperties wechatProperties) {
        STORAGE_MAP.put(wechatProperties.getAppId(), wechatProperties);
    }

    @Override
    public void putAccessToken(String appId, WechatATokenRes wechatATokenRes) {
        ACCESSTOKEN_MAP.put(appId, wechatATokenRes);
    }

    @Override
    public WechatATokenRes getAccessToken(String appId) {
        return ACCESSTOKEN_MAP.get(appId);
    }


    @Override
    public Map<String, WechatATokenRes> getAccessTokenMap() {
        return Collections.unmodifiableMap(ACCESSTOKEN_MAP);
    }

    @Override
    public WechatJsapiTicketRes getJsapiTicket(String appId) {
        return JSAPITICKET_MAP.get(appId);
    }

    @Override
    public void putJsapiTicket(String appId, WechatJsapiTicketRes jsapiTicketProperties) {
        JSAPITICKET_MAP.put(appId, jsapiTicketProperties);
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
        return false;
    }

    @Override
    public void setQrStatus(WechatMpQrcodeRes qrcode, Integer status) {

    }

    @Override
    public void setQrStatus(WechatMpQrcodeRes qrcode, Integer status, Integer expireIn) {

    }


    @Override
    public WechatMpQrcodeRes getQrStatus(String searchId) {
        return null;
    }

}
