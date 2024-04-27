package top.zsmile.tool.wechat.mp.bean;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

 /**
 * 微信公众号 accessToken
 */
public class WechatATokenRes {

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * 有效时长, 单位s
     */
    private Integer expireIn;

    /**
     * 过期时间，单位s
     */
    private Long expireTime;


    private WechatATokenRes() {

    }

    public static final WechatATokenRes create(String accessToken, Integer expireIn) {
        WechatATokenRes wechatATokenRes = new WechatATokenRes();
        wechatATokenRes.accessToken = accessToken;
        wechatATokenRes.expireIn = expireIn;
        wechatATokenRes.expireTime = DateUtils.addSeconds(new Date(), expireIn).getTime() / 1000;
        return wechatATokenRes;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Integer expireIn) {
        this.expireIn = expireIn;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
}
