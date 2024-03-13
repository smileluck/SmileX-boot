package top.zsmile.tool.wechat.gzh.wxgzh.utils;

import com.air.basis.modules.wechat.wxgzh.config.WechatConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public class WechatHandleUtil {
    private static Logger logger = LoggerFactory.getLogger(WechatHandleUtil.class);

    /**
     * 请求响应
     *
     * @param map
     * @return
     */
    public static String reqHandle(Map<String, String> map, String timestamp, String nonce) throws AesException {
        String res = null;
        switch (map.get("MsgType")) {
            //文本消息
            case "text":
                res = checkText(map);
                break;
            case "image":
                res = checkImage(map);
                break;
        }
        if (WechatConfig.AESState) {
//            WXBizMsgCrypt pc = new WXBizMsgCrypt(WechatConfig.TOKEN, WechatConfig.ENCODE_AES_kEY, WechatConfig.APP_ID);
            String secretRes = WechatConfig.pc.encryptMsg(res, timestamp, nonce);
            return secretRes;
        } else {
            return res;
        }
    }

    /**
     * 文本类型处理
     *
     * @param map
     * @return
     */
    public static String checkText(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<xml>");
        stringBuilder.append("<ToUserName><![CDATA[" + map.get("FromUserName") + "]]></ToUserName>");
        stringBuilder.append("<FromUserName><![CDATA[" + WechatConfig.USER_NAME + "]]></FromUserName>");
        stringBuilder.append("<CreateTime>" + new Date().getTime() / 1000 + "</CreateTime>");
        switch (map.get("Content")) {
            case "大佬牛逼":
                stringBuilder.append("<MsgType><![CDATA[text]]></MsgType>");
                stringBuilder.append("<Content><![CDATA[赖牛逼，今晚加班]]></Content>");
                break;
            case "大佬优秀":
                stringBuilder.append("<MsgType><![CDATA[text]]></MsgType>");
                stringBuilder.append("<Content><![CDATA[周牛逼，今晚加班]]></Content>");
                break;
            case "大佬天秀":
                stringBuilder.append("<MsgType><![CDATA[text]]></MsgType>");
                stringBuilder.append("<Content><![CDATA[郭牛逼，今晚加班]]></Content>");
                break;
            default:
                stringBuilder.append("<MsgType><![CDATA[text]]></MsgType>");
                stringBuilder.append("<Content><![CDATA[复读机：" + map.get("Content") + "]]></Content>");
                break;
        }
        stringBuilder.append("</xml>");
        return stringBuilder.toString();
    }


    /**
     * 图片类型处理
     *
     * @param map
     * @return
     */
    public static String checkImage(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        logger.info("图片url:" + map.get("PicUrl"));
        stringBuilder.append("<xml>");
        stringBuilder.append("<ToUserName><![CDATA[" + map.get("FromUserName") + "]]></ToUserName>");
        stringBuilder.append("<FromUserName><![CDATA[" + WechatConfig.USER_NAME + "]]></FromUserName>");
        stringBuilder.append("<CreateTime>" + new Date().getTime() / 1000 + "</CreateTime>");
        switch (map.get("Content")) {
            default:
                stringBuilder.append("<MsgType><![CDATA[image]]></MsgType>");
                stringBuilder.append("<Image><MediaId><![CDATA[" + map.get("MediaId") + "]]></MediaId></Image>");
                break;
        }
        stringBuilder.append("</xml>");
        return stringBuilder.toString();
    }


}
