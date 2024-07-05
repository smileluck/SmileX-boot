package top.zsmile.tool.wechat.mini.service.impl;

import top.zsmile.common.core.exception.SXException;
import top.zsmile.tool.wechat.mini.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mini.bean.WechatMiniOpenid;
import top.zsmile.tool.wechat.mini.bean.WechatMiniPhoneInfo;
import top.zsmile.tool.wechat.mini.properties.WechatMiniProperties;
import top.zsmile.tool.wechat.mini.service.AbstractWechatMiniStorageService;
import top.zsmile.tool.wechat.mini.service.IWechatMiniService;
import top.zsmile.tool.wechat.mini.utils.WechatMiniHttps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class WechatMiniServiceImpl implements IWechatMiniService {


    private static final Logger logger = LoggerFactory.getLogger(WechatMiniServiceImpl.class);

    @Resource
    private AbstractWechatMiniStorageService wechatStorageService;

    @Resource
    private IWechatMiniService wechatMiniService;

    @Override
    public boolean checkConfig(String appId) {
        return wechatStorageService.checkWechatMp(appId);
    }

    @Override
    public WechatATokenRes getAccessToken(String appId) {
        WechatATokenRes token = wechatStorageService.getAccessToken(appId);
        if (token == null) {
            token = fetchAccessToken(appId);
        }
        return token;
    }

    @Override
    public void refreshAccessToken() {
        refreshAccessToken(null);
    }

    @Override
    public void refreshAccessToken(String appId) {
        logger.info("refresh accessToken");
        long l = LocalDateTime.now().getNano() / 1000;
        if (appId == null) {
            Map<String, WechatMiniProperties> wechatMpMap = wechatStorageService.getWechatMpMap();
            Map<String, WechatATokenRes> accessTokenMap = wechatStorageService.getAccessTokenMap();
            for (Map.Entry<String, WechatMiniProperties> entry : wechatMpMap.entrySet()) {
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
    public WechatMiniPhoneInfo getPhoneNumber(String code) {
        return getPhoneNumber(wechatStorageService.getDefaultAppId(), code);
    }

    @Override
    public WechatMiniPhoneInfo getPhoneNumber(String appId, String code) {
        WechatATokenRes accessToken = getAccessToken(appId);
        return WechatMiniHttps.getPhoneNumber(accessToken.getAccessToken(), code);
    }

    @Override
    public WechatMiniOpenid code2Session(String code) {
        return code2Session(wechatStorageService.getDefaultAppId(), code);
    }

    @Override
    public WechatMiniOpenid code2Session(String appId, String code) {
        WechatMiniProperties wechatMp = wechatStorageService.getWechatMp(appId);
        WechatMiniOpenid wechatMiniOpenid = WechatMiniHttps.code2Session(wechatMp.getAppId(), wechatMp.getAppSecret(), code);
        if (wechatMiniOpenid == null) {
            throw new SXException("获取用户信息失败");
        }
        return wechatMiniOpenid;
    }

    private WechatATokenRes fetchAccessToken(String appId) {
        return fetchAccessToken(wechatStorageService.getWechatMp(appId));
    }

    private WechatATokenRes fetchAccessToken(WechatMiniProperties wechatMp) {
        if (wechatMp != null) {
            WechatATokenRes wechatATokenRes = WechatMiniHttps.getAccessToken(wechatMp.getAppId(), wechatMp.getAppSecret());
            if (wechatATokenRes != null) {
                wechatStorageService.putAccessToken(wechatMp.getAppId(), wechatATokenRes);
            }
            return wechatATokenRes;
        }
        return null;
    }

}
