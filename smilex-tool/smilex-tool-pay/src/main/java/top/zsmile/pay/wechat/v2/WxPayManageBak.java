package top.zsmile.pay.wechat.v2;

import lombok.extern.slf4j.Slf4j;
import top.zsmile.pay.enums.WxV2TradeTypeEnum;
import top.zsmile.pay.wechat.v2.config.WxPayConfig;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 微信接口库
 */
@Slf4j
public class WxPayManageBak {

    public static SortedMap<String, String> unifiedOrder(WxPayConfig config, Map<String, String> data) throws Exception {
        SortedMap<String, String> finalpackage = null;
        WxPayCore wxpay = new WxPayCore(config);
        Map<String, String> resp = null;
        resp = wxpay.unifiedOrder(data);
        log.info("unifiedOrder Res: " + resp.toString());
        if (WxPayUtil.checkResponState(resp)) {
            String timestamp = System.currentTimeMillis() / 1000L + "";
            finalpackage = new TreeMap<String, String>();
            String prepayid = null;
            if (data.get("trade_type").equals(WxV2TradeTypeEnum.JSAPI.getValue())) {
                prepayid = "prepay_id=" + resp.get("prepay_id");
                finalpackage.put("signType", "MD5");
                finalpackage.put("package", prepayid);
                if (config.getMchType() == 1) {
                    finalpackage.put("appId", resp.get("appid"));
                } else {
                    finalpackage.put("appId", resp.get("sub_appid"));
                }
                finalpackage.put("timeStamp", timestamp);
                finalpackage.put("nonceStr", resp.get("nonce_str"));
                String signature = WxPayUtil.generateSignature(finalpackage, config.getKey());
                finalpackage.put("paySign", signature);
            } else {
                prepayid = resp.get("prepay_id");
                finalpackage.put("partnerid", resp.get("mch_id"));
                finalpackage.put("package", "Sign=WXPay");
                finalpackage.put("prepayid", prepayid);
                if (config.getMchType() == 1) {
                    finalpackage.put("appid", resp.get("appid"));
                } else {
                    finalpackage.put("appid", resp.get("sub_appid"));
                }
                finalpackage.put("timestamp", timestamp);
                finalpackage.put("noncestr", resp.get("nonce_str"));
                String signature = WxPayUtil.generateSignature(finalpackage, config.getKey());
                finalpackage.put("sign", signature);
            }
        }
        return finalpackage;
    }


    /**
     * 退款
     */
    public static Map<String, String> refund(WxPayConfig config, Map<String, String> data) throws Exception {
        WxPayCore wxpay = new WxPayCore(config);
        Map<String, String> resp = wxpay.refund(data);
        log.info("refund res: " + resp);
        return resp;
    }


    /**
     * 查询订单
     */
    public static Map<String, String> searchOrder(WxPayConfig config, Map<String, String> data) throws Exception {
        WxPayCore wxPay = new WxPayCore(config);
        Map<String, String> resMap = wxPay.orderQuery(data);
        log.info("searchOrder res: " + resMap);
        return resMap;
    }


    /**
     * 查询退款
     */
    public static Map<String, String> refundQuery(WxPayConfig config, Map<String, String> data) throws Exception {
        WxPayCore wxPay = new WxPayCore(config);
        Map<String, String> resMap = wxPay.refundQuery(data);
        log.info("refundQuery res: " + resMap);
        return resMap;
    }

    /**
     * 撤销订单
     */
    public static Map<String, String> reverse(WxPayConfig config, Map<String, String> data) throws Exception {
        WxPayCore wxPay = new WxPayCore(config);
        Map<String, String> resMap = wxPay.reverse(data);
        log.info("reverse res: " + resMap);
        return resMap;
    }


    /**
     * 关闭订单
     */
    public static Map<String, String> closeOrder(WxPayConfig config, Map<String, String> data) throws Exception {
        WxPayCore wxPay = new WxPayCore(config);
        Map<String, String> resMap = wxPay.closeOrder(data);
        log.info("closeOrder res: " + resMap);
        return resMap;
    }


    /**
     * 下载对账单
     */
    public static Map<String, String> downloadBill(WxPayConfig config, Map<String, String> data) throws Exception {
        WxPayCore wxPay = new WxPayCore(config);
        Map<String, String> resMap = wxPay.downloadBill(data);
        log.info("downloadBill res: " + resMap);
        return resMap;
    }


    /**
     * 转换短链接
     */
    public static Map<String, String> shortUrl(WxPayConfig config, Map<String, String> data) throws Exception {
        WxPayCore wxPay = new WxPayCore(config);
        Map<String, String> resMap = wxPay.shortUrl(data);
        log.info("shortUrl res: " + resMap);
        return resMap;
    }

    /**
     * 授权码查询openid
     */
    public static Map<String, String> authCodeToOpenid(WxPayConfig config, Map<String, String> data) throws Exception {
        WxPayCore wxPay = new WxPayCore(config);
        Map<String, String> resMap = wxPay.authCodeToOpenid(data);
        log.info("authCodeToOpenid res: " + resMap);
        return resMap;
    }

    /**
     * 企业付款到零钱
     */
    public static Map<String, String> transfersWallet(WxPayConfig config, Map<String, String> data) throws Exception {
        WxPayCore wxPay = new WxPayCore(config);
        Map<String, String> resMap = wxPay.transfersWallet(data);
        log.info("transfersWallet res: " + resMap);
        return resMap;
    }


    /**
     * 企业付款到零钱查询
     */
    public static Map<String, String> transfersWalletInfo(WxPayConfig config, Map<String, String> data) throws Exception {
        WxPayCore wxPay = new WxPayCore(config);
        Map<String, String> resMap = wxPay.transfersWalletInfo(data);
        log.info("transfersWallet res: " + resMap);
        return resMap;
    }

//    /**
//     * 获取openid
//     */
//    public static JSONObject getOpenid(String appid, String secret, String code) {
//        JSONObject jsonObject = null;
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("appid", appid);
//        params.put("secret", secret);
//        params.put("grant_type", "authorization_code");
//        params.put("js_code", code);
//
//        jsonObject = JSON.parseObject(OkHttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", params));
//
//        log.info("getOpenid res: " + jsonObject);
//        return jsonObject;
//    }
}
