package top.zsmile.tool.wechat.mp.service.impl;

import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import top.zsmile.tool.wechat.mp.bean.*;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;
import top.zsmile.tool.wechat.mp.constant.WechatMpConstant;
import top.zsmile.tool.wechat.mp.handler.MessageRouter;
import top.zsmile.tool.wechat.mp.properties.WechatMpProperties;
import top.zsmile.tool.wechat.mp.service.AbstractWechatStorageService;
import top.zsmile.tool.wechat.mp.service.IWechatCryptoService;
import top.zsmile.tool.wechat.mp.service.IWechatMpService;
import top.zsmile.tool.wechat.mp.utils.WechatHttps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Service
public class WechatMpServiceImpl implements IWechatMpService {

    private static final Logger logger = LoggerFactory.getLogger(WechatMpServiceImpl.class);

    @Resource
    private AbstractWechatStorageService wechatStorageService;

    @Resource
    private IWechatMpService wechatMpService;

    @Resource
    private IWechatCryptoService wechatCryptoService;

    @Override
    public boolean checkConfig(String appId) {
        return wechatStorageService.checkWechatMp(appId);
    }

    @Override
    public boolean checkSignature(WechatNotifyParams params) {
        return checkSignature(params.getSignature(), params.getTimestamp(), params.getNonce());
    }

    @Override
    public boolean checkSignature(WechatConfigParams params) {
        return checkSignature(params.getSignature(), params.getTimestamp(), params.getNonce());
    }

    @Override
    public boolean checkSignature(String signature, String timestamp, String nonce) {
        return checkSignature(wechatStorageService.getDefaultAppId(), signature, timestamp, nonce);
    }

    @Override
    public boolean checkSignature(String appId, String signature, String timestamp, String nonce) {
        if (StringUtils.isAnyBlank(appId, signature, timestamp, nonce)) {
            throw new IllegalArgumentException("参数异常");
        }

        WechatMpProperties wechatMp = wechatStorageService.getWechatMpNotNull(appId);

        String newSignature = genSignature(wechatMp.getToken(), timestamp, nonce);
        if (newSignature.equals(signature)) {
            return true;
        }

        return false;
    }

    @Override
    public WechatATokenRes getAccessToken(String appId) {
        WechatATokenRes wechatATokenRes = wechatStorageService.getAccessToken(appId);
        if (wechatATokenRes == null) {
            wechatATokenRes = fetchAccessToken(appId);
        }
        return wechatATokenRes;
    }

    @Override
    public WechatJsapiTicketRes getJsapiTicket(String appId) {
        WechatJsapiTicketRes jsapiTicket = wechatStorageService.getJsapiTicket(appId);
        if (jsapiTicket == null) {
            WechatATokenRes accessToken = wechatStorageService.getAccessToken(appId);
            jsapiTicket = WechatHttps.getJsapiTicket(accessToken.getAccessToken());
            if (jsapiTicket != null) {
                wechatStorageService.putJsapiTicket(appId, jsapiTicket);
            }
        }
        return jsapiTicket;
    }

    @Override
    public WechatMpQrcodeRes createQrcode(String sceneId, WechatMpConstant.QrCodeType type) {
        return createQrcode(wechatStorageService.getDefaultAppId(), sceneId, type, null);
    }

    @Override
    public WechatMpQrcodeRes createQrcode(String sceneId, WechatMpConstant.QrCodeType type, Long userId) {
        return createQrcode(wechatStorageService.getDefaultAppId(), sceneId, type, userId);
    }

    @Override
    public WechatMpQrcodeRes createQrcode(String appId, String sceneId, WechatMpConstant.QrCodeType type, Long userId) {
        return createQrcode(appId, sceneId, type, userId, 180);
    }

    @Override
    public WechatMpQrcodeRes createQrcode(String appId, String sceneId, WechatMpConstant.QrCodeType type, Long userId, Integer expireIn) {
        return createQrcode(appId, sceneId, type, userId, expireIn, false);
    }

    @Override
    public WechatMpQrcodeRes createQrcode(String appId, String sceneId, WechatMpConstant.QrCodeType type, Long userId, Integer expireIn, boolean perpetual) {
        JSONObject json = new JSONObject();
        if (perpetual) {
            json.put("action_name", "QR_LIMIT_STR_SCENE");
        } else {
            json.put("action_name", "QR_STR_SCENE");
            json.put("expire_seconds", expireIn);
        }
        json.put("action_info", String.format("{\"scene\": {\"scene_str\": \"%s\"}}", sceneId));
        WechatATokenRes accessToken = wechatStorageService.getAccessToken(appId);
        WechatMpQrcodeRes qrcode = WechatHttps.createQrcode(accessToken.getAccessToken(), json.toJSONString());
        if (qrcode != null) {
            qrcode.setType(type.getType());
            qrcode.setUserId(userId);
        }
        wechatStorageService.setQrStatus(qrcode, WechatMpConstant.QrCodeStatus.LOOP);
        return qrcode;
    }

    @Override
    public WechatMpQrcodeRes createQrcode(Integer sceneId, WechatMpConstant.QrCodeType type) {
        return createQrcode(wechatStorageService.getDefaultAppId(), sceneId, type, null);
    }

    @Override
    public WechatMpQrcodeRes createQrcode(Integer sceneId, WechatMpConstant.QrCodeType type, Long userId) {
        return createQrcode(wechatStorageService.getDefaultAppId(), sceneId, type, userId);
    }

    @Override
    public WechatMpQrcodeRes createQrcode(String appId, Integer sceneId, WechatMpConstant.QrCodeType type, Long userId) {
        return createQrcode(appId, sceneId, type, userId, 180);
    }

    @Override
    public WechatMpQrcodeRes createQrcode(String appId, Integer sceneId, WechatMpConstant.QrCodeType type, Long userId, Integer expireIn) {
        return createQrcode(appId, sceneId, type, userId, expireIn, false);
    }

    @Override
    public WechatMpQrcodeRes createQrcode(String appId, Integer sceneId, WechatMpConstant.QrCodeType type, Long userId, Integer expireIn, boolean perpetual) {
        JSONObject json = new JSONObject();
        if (perpetual) {
            json.put("action_name", "QR_LIMIT_SCENE");
        } else {
            json.put("action_name", "QR_SCENE");
            json.put("expire_seconds", expireIn);
        }
        json.put("action_info", String.format("{\"scene\": {\"scene_id\": %d}}", sceneId));
        WechatATokenRes accessToken = wechatStorageService.getAccessToken(appId);
        WechatMpQrcodeRes qrcode = WechatHttps.createQrcode(accessToken.getAccessToken(), json.toJSONString());
        if (qrcode != null) {
            qrcode.setType(type.getType());
            qrcode.setUserId(userId);
        }
        wechatStorageService.setQrStatus(qrcode, WechatMpConstant.QrCodeStatus.LOOP);
        return qrcode;
    }

    @Override
    public WechatMpQrcodeRes getQrcodeStatus(String searchId) {
        return wechatStorageService.getQrStatus(searchId);
    }


    @Override
    public void refreshAccessToken() {
        refreshAccessToken(null);
    }

    @Override
    public void refreshAccessToken(String appId) {
        logger.info("refresh accessToken");
        long l = new Date().getTime() / 1000;
        if (appId == null) {
            Map<String, WechatMpProperties> wechatMpMap = wechatStorageService.getWechatMpMap();
            Map<String, WechatATokenRes> accessTokenMap = wechatStorageService.getAccessTokenMap();
            for (Map.Entry<String, WechatMpProperties> entry : wechatMpMap.entrySet()) {
                logger.debug("refresh appId: {}", entry.getKey());
                WechatATokenRes wechatATokenRes = accessTokenMap.get(entry.getKey());
                if (wechatATokenRes != null) {
                    // 剩余时间不足30分钟
                    long expireTime = wechatATokenRes.getExpireTime() - 1800;
                    if (l > expireTime) {
                        fetchAccessToken(entry.getValue());
                    }
                } else {
                    fetchAccessToken(entry.getKey());
                }
            }
        } else {
            logger.debug("refresh appId: {}", appId);
            WechatATokenRes accessToken = wechatStorageService.getAccessToken(appId);
            // 剩余时间不足30分钟
            long expireTime = accessToken.getExpireTime() - 1800;
            if (l > expireTime) {
                fetchAccessToken(appId);
            }
        }
    }

    @Override
    public boolean repeatNotify(WechatNotifyParams params) {
        return wechatStorageService.setNXRepeat(params);
    }

    @Override
    public String handleMessage(WechatNotifyParams params, String postData) {
        return handleMessage(wechatStorageService.getDefaultAppId(), params, postData);
    }

    @Override
    public String handleMessage(String appId, WechatNotifyParams params, String postData) {
        String encType = params.getEncrypt_type();
        if (encType == null) {
            WechatMpInMessage wechatMpInMessage = WechatMpInMessage.fromXML(postData);
            logger.debug("WechatMp Receive => {}", wechatMpInMessage);
            String exec = MessageRouter.exec(params.getOpenid(), wechatMpInMessage);
            if (exec == null) {
                return "";
            }
            return exec;
        } else if ("aes".equalsIgnoreCase(encType)) {
            WechatMpInMessage wechatMpInMessage = wechatCryptoService.decrypt(appId, params, postData);
            logger.debug("WechatMp Receive => {}", wechatMpInMessage);
            String exec = MessageRouter.exec(params.getOpenid(), wechatMpInMessage);
            if (exec == null) {
                return "";
            }
            return wechatCryptoService.encrypt(appId, exec, params.getTimestamp(), params.getNonce());
        }
        return "";
    }

    private WechatATokenRes fetchAccessToken(String appId) {
        return fetchAccessToken(wechatStorageService.getWechatMp(appId));
    }

    private WechatATokenRes fetchAccessToken(WechatMpProperties wechatMp) {
        if (wechatMp != null) {
            WechatATokenRes wechatATokenRes = WechatHttps.getAccessToken(wechatMp);
            if (wechatATokenRes != null) {
                wechatStorageService.putAccessToken(wechatMp.getAppId(), wechatATokenRes);
            }
            return wechatATokenRes;
        }
        return null;
    }

    private String genSignature(String token, String timestamp, String nonce) {
        // 1. 将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);

        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        String str = sb.toString();

        // 3. 使用Apache Commons Codec对字符串进行SHA1加密
        return DigestUtils.sha1Hex(str);
    }
}

