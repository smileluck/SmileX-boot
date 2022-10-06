package top.zsmile.pay.enums;

public enum WxV2TradeTypeEnum {

    // APP
    APP("APP"),
    // 小程序
    JSAPI("JSAPI"),
    // 原生Native
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
