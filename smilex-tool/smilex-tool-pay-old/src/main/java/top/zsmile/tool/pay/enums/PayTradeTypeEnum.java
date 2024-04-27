package top.zsmile.tool.pay.enums;

/**
 * 内部交易类型分类
 */
public enum PayTradeTypeEnum {

    // 付款码
    PAYMENT_CODE("PAYMENT_CODE"),
    // JSAPI支付
    JSAPI("JSAPI"),
    // Native("Native),微信扫一扫支付
    NATIVE("NATIVE"),
    // APP支付
    APP("APP"),
    // H5支付
    H5_PAY("H5_PAY"),
    // 小程序支付
    MINI_PAY("MINI_PAY"),
    // 刷脸支付
    FACE_PAY("FACE_PAY");

    private String value;

    public String getValue() {
        return value;
    }

    private PayTradeTypeEnum(String value) {
        this.value = value;
    }
}
