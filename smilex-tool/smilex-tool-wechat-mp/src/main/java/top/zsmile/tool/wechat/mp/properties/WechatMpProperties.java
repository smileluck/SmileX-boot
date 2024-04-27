package top.zsmile.tool.wechat.mp.properties;

/**
 * 公众号配置
 */
public class WechatMpProperties {

    /**
     * appId
     */
    private String appId;

    /**
     * appSecret
     */
    private String appSecret;

    /**
     * token
     */
    private String token;

    /**
     * aesKey
     */
    private String aeskey;

    /**
     * 重定向Url
     */
    private String redirectUrl;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAeskey() {
        return aeskey;
    }

    public void setAeskey(String aeskey) {
        this.aeskey = aeskey;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
