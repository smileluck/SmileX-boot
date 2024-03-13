package top.zsmile.tool.wechat.gzh.wxgzh.config;


import com.air.basis.modules.wechat.wxgzh.utils.WXBizMsgCrypt;

public class WechatConfig {

    //公众号id
    public static String USER_NAME;
    //加密/解密密钥
    public static String ENCODE_AES_kEY;

    //加密状态
    public static boolean AESState = false;

    //自定义token, 用作生成签名,从而验证安全性
    public static String TOKEN;
    //公众号信息
    public static String APP_ID;
    public static String APP_SECRET;
    //权限token
    public static String ACCESS_TOKEN;
    //公众号用于调用微信JS接口的临时票据
    public static String JSAPI_TICKET;

    public static WXBizMsgCrypt pc;

}
