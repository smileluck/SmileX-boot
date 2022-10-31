package top.zsmile.pay.enums;

/**
 * 退款资金来源
 */
public enum RefundAccountEnum {

    UNSETTLED_FUNDS("REFUND_SOURCE_UNSETTLED_FUNDS"),
    RECHARGE_FUNDS("REFUND_SOURCE_RECHARGE_FUNDS");

    private String value;

    public String getValue() {
        return value;
    }

    private RefundAccountEnum(String value) {
        this.value = value;
    }
}
