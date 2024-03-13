package top.zsmile.tool.wechat.gzh.wxgzh.utils;

import com.air.basis.common.libs.http.OkHttpUtil;
import com.air.basis.modules.wechat.wxgzh.config.WechatConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class WechatSignUtil {

    private static Logger logger = LoggerFactory.getLogger(WechatSignUtil.class);

    /**
     * 获取AccessToken
     */
    public static String getAccessToken() {
        try {
            String res = OkHttpUtil.get("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid=" + WechatConfig.APP_ID + "&secret=" + WechatConfig.APP_SECRET);
            JSONObject jsonObject = JSON.parseObject(res);
            if (jsonObject.containsKey("access_token")) {
                WechatConfig.ACCESS_TOKEN = jsonObject.getString("access_token");
                logger.info("accessToken:" + WechatConfig.ACCESS_TOKEN);
                return WechatConfig.ACCESS_TOKEN;
            } else {
                logger.error("请求异常" + jsonObject.toJSONString());
                return null;
            }
        } catch (Exception e) {
            logger.error("获取accessToken异常" + e.getMessage());
            return null;
        }
    }

    public static String getJsapiTicket() {
        try {
            String res = OkHttpUtil.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket", "access_token=" + WechatConfig.ACCESS_TOKEN + "&type=jsapi");
            JSONObject jsonObject = JSON.parseObject(res);
            if (jsonObject.getIntValue("errcode") == 0) {
                WechatConfig.JSAPI_TICKET = jsonObject.getString("ticket");
                logger.info("ticket:" + WechatConfig.JSAPI_TICKET);
                return WechatConfig.JSAPI_TICKET;
            } else {
                logger.error("请求异常" + jsonObject.toJSONString());
                return null;
            }
        } catch (Exception e) {
            logger.error("获取ticket异常" + e.getMessage());
            return null;
        }
    }

    /**
     * 签名校验
     *
     * @param timestamp
     * @param nonce
     * @param signature
     * @return
     */
    public static boolean checkSign(String timestamp, String nonce, String signature) {
        //签名
        String mySignature = sign(timestamp, nonce);
        //校验微信服务器传递过来的签名 和  加密后的字符串是否一致, 若一致则签名通过
        if (mySignature.equals(signature) && !"".equals(signature) && !"".equals(mySignature)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 签名
     *
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String sign(String timestamp, String nonce) {
        //将token、timestamp、nonce三个参数进行字典序排序并拼接为一个字符串
        String sort = sort(WechatConfig.TOKEN, timestamp, nonce);
        String mySignature = shal(sort);
        return mySignature;
    }


    /**
     * 参数排序
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * jsapiticket签名算法
     */
    public static String ticketSign(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        String[] keyArray = (String[]) keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        int len = keyArray.length;
        for (int i = 0; i < len; i++) {
            String key = keyArray[i];
            String value = map.get(key).trim();
            if (value.length() > 0) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        ;
        String sign = shal(sb.substring(0, sb.length() - 1).toString());
        return sign;
    }


    /**
     * 字符串进行shal加密
     *
     * @param str
     * @return
     */
    public static String shal(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer(); // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
