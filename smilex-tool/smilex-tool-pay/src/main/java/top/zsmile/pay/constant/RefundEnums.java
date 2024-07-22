package top.zsmile.pay.constant;

public enum RefundEnums {
    SUCCESS("退款成功"),
    CLOSED("退款关闭"),

    PROCESSING("退款处理中"),
    ABNORMAL("退款异常")
    ;
    private String label;
    RefundEnums(String label) {
        this.label=label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
