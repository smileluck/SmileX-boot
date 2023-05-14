package top.zsmile.tool.pay.wechat.v2.config;

import java.io.InputStream;

public interface IWxPay {
    String getAppID();

    String getMchID();

    String getSubAppID();

    String getSubMchID();

    String getKey();

    int getMchType();

    InputStream getCertStream();

    int getHttpConnectTimeoutMs();

    int getHttpReadTimeoutMs();
}
