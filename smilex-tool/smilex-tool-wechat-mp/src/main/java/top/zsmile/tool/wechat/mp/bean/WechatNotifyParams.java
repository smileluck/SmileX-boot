package top.zsmile.tool.wechat.mp.bean;

/**
 * 微信通知参数
 */
public class WechatNotifyParams {

    /**
     * 签名
     */
    private String signature;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 随机数
     */
    private String nonce;
    /**
     * 微信openid
     */
    private String openid;

    /**
     * 加密类型
     */
    private String encrypt_type;

    /**
     * 消息签名
     */
    private String msg_signature;

//    public String getEncType() {
//        return encrypt_type;
//    }
//
//    public void setEncType(String encType) {
//        this.encrypt_type = encType;
//    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getEncrypt_type() {
        return encrypt_type;
    }

    public void setEncrypt_type(String encrypt_type) {
        this.encrypt_type = encrypt_type;
    }

    public String getMsg_signature() {
        return msg_signature;
    }

    public void setMsg_signature(String msg_signature) {
        this.msg_signature = msg_signature;
    }
}
