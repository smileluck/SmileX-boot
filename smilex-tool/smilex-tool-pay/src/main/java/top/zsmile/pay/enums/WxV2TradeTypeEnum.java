package top.zsmile.pay.enums;

public enum WxV2TradeTypeEnum {

    // APP
    APP("APP"),
    // 小程序
    JSAPI("JSAPI"),
    // 微信扫一扫支付
    NATIVE("NATIVE"),
    // H5支付
    MWEB("MWEB");

    private String value;

    public String getValue() {
        return this.value;
    }

    private WxV2TradeTypeEnum(String value) {
        this.value = value;
    }
}
