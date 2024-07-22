package top.zsmile.pay.properties;

public class AliPayConfigProperties {
    /**
     * id 配置ID
     */
    private String id;
    /**
     * appid 应用ID
     */
    private String appid;
    /**
     * privateKey 应用私钥
     */
    private String privateKey;
    /**
     * alipayPublicKey  阿里公钥
     */
    private String alipayPublicKey;
    /**
     * serveUrl 服务请求地址
     */
    private String serveUrl;
    /**
     * 通知URL
     */
    private String notifyUrl;
    /**
     * 退款通知URL
     */
    private String notifyRefundUrl;

    /**
     * 格式
     */
    private String format = "json";
    /**
     * 字符格式
     */
    private String charset = "UTF-8";
    /**
     * 签名格式
     */
    private String signType = "RSA2";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getServeUrl() {
        return serveUrl;
    }

    public void setServeUrl(String serveUrl) {
        this.serveUrl = serveUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getNotifyRefundUrl() {
        return notifyRefundUrl;
    }

    public void setNotifyRefundUrl(String notifyRefundUrl) {
        this.notifyRefundUrl = notifyRefundUrl;
    }
}
