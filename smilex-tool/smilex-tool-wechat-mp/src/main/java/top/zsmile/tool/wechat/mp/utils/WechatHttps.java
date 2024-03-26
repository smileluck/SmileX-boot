package top.zsmile.tool.wechat.mp.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import top.zsmile.common.core.utils.OkHttpUtil;
import top.zsmile.tool.wechat.mp.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mp.bean.WechatJsapiTicketRes;
import top.zsmile.tool.wechat.mp.bean.WechatMpQrcodeRes;
import top.zsmile.tool.wechat.mp.constant.WechatHttpConstant;
import top.zsmile.tool.wechat.mp.properties.WechatMpProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信请求工具
 */
public class WechatHttps {

    private final static Logger logger = LoggerFactory.getLogger(WechatHttps.class);

    private WechatHttps() {
    }

    public static WechatMpQrcodeRes createQrcode(String accessToken, String json) {
        String res = OkHttpUtil.postJson(WechatHttpConstant.QRCODE_CREATE + "?access_token=" + accessToken, json);
        if (StringUtils.isNotBlank(res)) {
            JSONObject resJson = JSON.parseObject(res);
            if (resJson.containsKey("ticket")) {
                return WechatMpQrcodeRes.create(resJson.getString("ticket"), resJson.getInteger("expire_seconds"), resJson.getString("url"));
            }
            logger.error("fetch createQrcode error => {}", resJson.toJSONString());
        }
        return null;
    }

    public static WechatJsapiTicketRes getJsapiTicket(String accessToken) {
        String res = OkHttpUtil.get(WechatHttpConstant.GET_TICKET, "access_token=" + accessToken + "&type=jsapi");
        if (StringUtils.isNotBlank(res)) {
            JSONObject resJson = JSON.parseObject(res);
            if (resJson.getIntValue("errcode") == 0) {
                return WechatJsapiTicketRes.create(resJson.getString("ticket"), resJson.getInteger("expires_in"));
            }
            logger.error("fetch JsapiTicket error => {}", resJson.toJSONString());
        }
        return null;
    }

    public static WechatATokenRes getAccessToken(WechatMpProperties wechatMp) {
        String res = OkHttpUtil.get(WechatHttpConstant.TOKEN, "grant_type=client_credential&appid=" + wechatMp.getAppId() + "&secret=" + wechatMp.getAppSecret());
        if (StringUtils.isNotBlank(res)) {
            JSONObject resJson = JSON.parseObject(res);
            if (resJson.containsKey("access_token")) {
                return WechatATokenRes.create(resJson.getString("access_token"), resJson.getInteger("expires_in"));
            }
            logger.error("fetch AccessToken error => {}", resJson.toJSONString());
        }
        return null;
    }
}
