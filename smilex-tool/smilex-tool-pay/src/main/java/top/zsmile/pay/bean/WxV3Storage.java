package top.zsmile.pay.bean;

import com.wechat.pay.java.core.RSAAutoCertificateConfig;

/**
 * 微信支付V3存储
 */
public class WxV3Storage {
    /**
     * 商户号
     */
    private String appid;
    /**
     * 商户号
     */
    private String mchid;
    /**
     * 回调地址
     */
    private String notifyUrl;

    /**
     * 配置
     */
    private RSAAutoCertificateConfig config;

    public WxV3Storage(String appid, String mchid, String notifyUrl, RSAAutoCertificateConfig config) {
        this.appid = appid;
        this.mchid = mchid;
        this.notifyUrl = notifyUrl;
        this.config = config;
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

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public RSAAutoCertificateConfig getConfig() {
        return config;
    }

    public void setConfig(RSAAutoCertificateConfig config) {
        this.config = config;
    }
}
