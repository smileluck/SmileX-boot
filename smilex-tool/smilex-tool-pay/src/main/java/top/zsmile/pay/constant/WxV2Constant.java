package top.zsmile.pay.constant;

/**
 * v2: https://pay.weixin.qq.com/wiki/doc/api/index.html
 */
public class WxV2Constant {
    /********************变量名*****************/
    public static final String FAIL = "FAIL";
    public static final String SUCCESS = "SUCCESS";
    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";
    public static final String FIELD_APP_ID = "appid";
    public static final String FIELD_MCH_ID = "mch_id";
    public static final String FIELD_NONCE_STR = "nonce_str";
    public static final String FIELD_SUB_APP_ID = "sub_appid";
    public static final String FIELD_SUB_MCH_ID = "sub_mch_id";

    /********************返回值*****************/
    public static final String FIELD_RETURN_CODE = "return_code";
    public static final String FIELD_RETURN_MSG = "return_msg";
    public static final String FIELD_RESULT_CODE = "result_code";
    public static final String FIELD_ERR_CODE = "err_code";
    public static final String FIELD_ERR_CODE_DES = "err_code_des";
    public static final String FIELD_DEVICE_INFO = "device_info";
    public static final String FIELD_TRADE_TYPE = "trade_type";
    public static final String FIELD_PREPAY_ID = "prepay_id";
    public static final String FIELD_CODE_URL = "code_url";
    public static final String FIELD_MWEB_URL = "mweb_url";



    /********************正式系统*****************/
    //付款码                    API：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10&index=1
    public static final String MICROPAY_URL = "https://api.mch.weixin.qq.com/pay/micropay";
    //统一下单                  API：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1
    public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //查询订单                  API：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_2
    public static final String ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    //撤销订单                  API：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_11&index=3
    public static final String REVERSE_URL = "https://api.mch.weixin.qq.com/secapi/pay/reverse";
    //关闭订单                  API：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_3
    public static final String CLOSEORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
    //申请退款                  API：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_4
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //查询退款                  API：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_5
    public static final String REFUNDQUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
    //下载对账单                API：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_6
    public static final String DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    //交易保障                  API：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_8&index=9
    public static final String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
    //转换短链接                API：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_9&index=10
    public static final String SHORTURL_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
    //授权码查询openid          API：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_13&index=9
    public static final String AUTHCODETOOPENID_URL = "https://api.mch.weixin.qq.com/tools/authcodetoopenid";
    //企业付款到零钱            API：https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
    public static final String TRANSFERSWALLET_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    //企业付款到零钱查询            API：https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
    public static final String TRANSFERSWALLETINFO_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";

    /********************仿真测试系统*****************/
    public static final String SANDBOX_MICROPAY_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL = "https://api.mch.weixin.qq.com/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL = "https://api.mch.weixin.qq.com/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL = "https://api.mch.weixin.qq.com/sandboxnew/tools/authcodetoopenid";
    public static final String SANDBOX_TRANSFERSWALLET_URL = "https://api.mch.weixin.qq.com/sandboxnew/mmpaymkttransfers/promotion/transfers";
    public static final String SANDBOX_TRANSFERSWALLETINFO_URL = "https://api.mch.weixin.qq.com/sandboxnew/mmpaymkttransfers/gettransferinfo";

}
