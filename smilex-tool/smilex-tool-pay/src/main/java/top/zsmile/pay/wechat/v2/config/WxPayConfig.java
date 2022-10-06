package top.zsmile.pay.wechat.v2.config;


import org.apache.commons.lang3.StringUtils;
import top.zsmile.pay.wechat.v2.config.IWxPay;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 微信支付配置
 */
public class WxPayConfig implements IWxPay {
    private String appid; //appid
    private String subAppid; //子appid
    private String mchid; //商户 ID
    private String subMchid;//子商戶id
    private String key; //商户平台设置的加密 key
    private String certPath; //证书路径
    private byte[] certData;//证书数据
    private int mchType;//1普通商户，2服务商


    /******配置*******/
    private int httpConnectTimeoutMs = 8000;//连接超时ms
    private int httpReadTimeoutMs = 10000;//读超时ms

    /**
     * 普通商户
     *
     * @param appid
     * @param mchid
     * @param key
     * @param certPath
     * @throws Exception
     */
    public WxPayConfig(String appid, String mchid, String key, String certPath) throws Exception {
        this.appid = appid;
        this.mchid = mchid;
        this.key = key;
        this.certPath = certPath;
        this.mchType = 1;
        this.getCertData();
    }

    /**
     * 服务商
     *
     * @param appid
     * @param subAppid
     * @param mchid
     * @param subMchid
     * @param key
     * @param certPath
     * @throws Exception
     */
    public WxPayConfig(String appid, String subAppid, String mchid, String subMchid, String key, String certPath) throws Exception {
        this.appid = appid;
        this.subAppid = subAppid;
        this.mchid = mchid;
        this.subMchid = subMchid;
        this.key = key;
        this.certPath = certPath;
        this.mchType = 2;
        this.getCertData();
    }

    public void getCertData() throws Exception {
        if (StringUtils.isNotBlank(this.certPath)) {
            File file = new File(certPath);
            InputStream certStream = new FileInputStream(file);
            this.certData = new byte[(int) file.length()];
            certStream.read(this.certData);
            certStream.close();
        }
    }


    @Override
    public String getAppID() {
        return this.appid;
    }

    @Override
    public String getMchID() {
        return this.mchid;
    }

    @Override
    public String getSubAppID() {
        return this.subAppid;
    }

    @Override
    public String getSubMchID() {
        return this.subMchid;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public int getMchType() {
        return this.mchType;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return this.httpConnectTimeoutMs;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return this.httpReadTimeoutMs;
    }

    @Override
    public String toString() {
        return "WechatConfig{" +
                "appid=\"" + this.appid + "\"," +
                "mchid=\"" + this.mchid + "\"," +
                "subappid=\"" + this.subAppid + "\"," +
                "submchid=\"" + this.subMchid + "\"," +
                "key=\"" + this.key + "\"," +
                "certPath=\"" + this.certPath + "\"}";
    }
}
