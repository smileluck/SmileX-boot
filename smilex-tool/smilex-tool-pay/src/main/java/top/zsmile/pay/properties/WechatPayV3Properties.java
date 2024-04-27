package top.zsmile.pay.properties;

public class WechatPayV3Properties {

    /**
     * 唯一ID 用来匹配信息使用
     */
    private String id;

    /**
     * 商户号
     */
    private String appid;
    /**
     * 商户号
     */
    private String mchid;
    /**
     * 商户APIV3密钥
     */
    private String apiKey;

    /**
     * 商户API私钥路径
     */
    private String privateKeyPath;
    /**
     * 商户证书序列号
     */
    private String mchSerialNum;
    /**
     * 商户api证书序列号
     */
    private String apiSerialNum;

    /**
     * 回调地址
     */
    private String notifyUrl;

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

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getMchSerialNum() {
        return mchSerialNum;
    }

    public void setMchSerialNum(String mchSerialNum) {
        this.mchSerialNum = mchSerialNum;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getApiSerialNum() {
        return apiSerialNum;
    }

    public void setApiSerialNum(String apiSerialNum) {
        this.apiSerialNum = apiSerialNum;
    }
}
