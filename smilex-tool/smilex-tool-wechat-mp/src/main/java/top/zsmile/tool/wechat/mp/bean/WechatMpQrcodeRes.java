package top.zsmile.tool.wechat.mp.bean;

public class WechatMpQrcodeRes {


    /**
     * 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
     */
    private String ticket;

    /**
     * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。
     */
    private Integer expireIn;

    /**
     * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    private String url;

    /**
     * 开放ID
     */
    private String openid;

    /**
     * 类型
     * {@link top.zsmile.tool.wechat.mp.constant.WechatConstant.QrCodeType}
     */
    private String type;

    /**
     * 状态
     * {@link top.zsmile.tool.wechat.mp.constant.WechatConstant.QrCodeStatus}
     */
    private Integer status;


    /**
     * 用户ID
     */
    private Long userId;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private WechatMpQrcodeRes() {
    }

    public static WechatMpQrcodeRes create(String ticket, Integer expireIn, String url) {
        WechatMpQrcodeRes wechatMpQrcodeRes = new WechatMpQrcodeRes();
        wechatMpQrcodeRes.setTicket(ticket);
        wechatMpQrcodeRes.setExpireIn(expireIn);
        wechatMpQrcodeRes.setUrl(url);
        return wechatMpQrcodeRes;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Integer expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
