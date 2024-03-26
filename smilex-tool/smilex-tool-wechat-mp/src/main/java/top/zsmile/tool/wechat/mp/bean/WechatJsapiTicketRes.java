package top.zsmile.tool.wechat.mp.bean;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * 微信公众号 accessToken
 */
public class WechatJsapiTicketRes {

    /**
     * jsapiTicket
     */
    private String jsapiTicket;

    /**
     * 有效时长, 单位s
     */
    private Integer expireIn;

    /**
     * 过期时间，单位s
     */
    private Long expireTime;


    private WechatJsapiTicketRes() {

    }

    public static final WechatJsapiTicketRes create(String jsapiTicket, Integer expireIn) {
        WechatJsapiTicketRes wechatATokenProperties = new WechatJsapiTicketRes();
        wechatATokenProperties.jsapiTicket = jsapiTicket;
        wechatATokenProperties.expireIn = expireIn;
        wechatATokenProperties.expireTime = DateUtils.addSeconds(new Date(), expireIn).getTime() / 1000;
        return wechatATokenProperties;
    }

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
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
