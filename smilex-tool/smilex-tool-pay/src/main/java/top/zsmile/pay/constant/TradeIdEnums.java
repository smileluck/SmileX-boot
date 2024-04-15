package top.zsmile.pay.constant;

/**
 * Trade Id 枚举
 */
public enum TradeIdEnums {
    MAIN("main");

    TradeIdEnums(String id) {
        this.id = id;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
