package top.zsmile.tool.wechat.mini.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import top.zsmile.common.core.utils.http.OkHttpUtil;
import top.zsmile.tool.wechat.mini.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mini.bean.WechatMiniOpenid;
import top.zsmile.tool.wechat.mini.bean.WechatMiniPhoneInfo;
import top.zsmile.tool.wechat.mini.constant.WechatMiniHttpConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信请求工具
 */
public class WechatMiniHttps {

    private final static Logger logger = LoggerFactory.getLogger(WechatMiniHttps.class);

    private WechatMiniHttps() {
    }

    /**
     * 获取ACCESSTOKEN
     *
     * @param appId     APPID
     * @param appSecret APPSECRET
     * @return result
     */
    public static WechatATokenRes getAccessToken(String appId, String appSecret) {
        String res = OkHttpUtil.postJson(WechatMiniHttpConstant.ACCESS_TOKEN + "?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret, Strings.EMPTY);
        if (StringUtils.isNotBlank(res)) {
            JSONObject resJson = JSON.parseObject(res);
            if (resJson.containsKey("access_token")) {
                return WechatATokenRes.create(resJson.getString("access_token"), resJson.getInteger("expires_in"));
            }
            logger.error("fetch createQrcode error => {}", resJson.toJSONString());
        }
        return null;
    }


    /**
     * 手机快速验证
     *
     * @param accessToken accessToken
     * @return result
     */
    public static WechatMiniPhoneInfo getPhoneNumber(String accessToken, String code) {
        JSONObject data = new JSONObject();
        data.put("code", code);
        String res = OkHttpUtil.postJson(WechatMiniHttpConstant.PHONE_NUMBER + "?access_token=" + accessToken, data.toJSONString());
        if (StringUtils.isNotBlank(res)) {
            JSONObject resJson = JSON.parseObject(res);
            if (resJson.getInteger("errcode").equals(0)) {
                return resJson.getObject("phone_info", WechatMiniPhoneInfo.class);
            }
            logger.error("fetch createQrcode error => {}", resJson.toJSONString());
        }
        return null;
    }


    /**
     * 手机快速验证
     *
     * @return result
     */
    public static WechatMiniOpenid code2Session(String appId, String secret, String code) {
        String res = OkHttpUtil.get(WechatMiniHttpConstant.CODE_SESSION, "appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code");
        if (StringUtils.isNotBlank(res)) {
            JSONObject resJson = JSON.parseObject(res);
            if (resJson.containsKey("openid")) {
                return JSON.parseObject(res, WechatMiniOpenid.class);
            }
            logger.error("fetch createQrcode error => {}", resJson.toJSONString());
        }
        return null;
    }


}
