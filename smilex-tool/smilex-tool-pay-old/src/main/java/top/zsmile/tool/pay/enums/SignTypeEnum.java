package top.zsmile.tool.pay.enums;

public enum SignTypeEnum {

    MD5("MD5"),
    HMACSHA256("HMAC-SHA256");

    private String value;

    private SignTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
