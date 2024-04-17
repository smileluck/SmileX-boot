package top.zsmile.pay.bean;

import com.alipay.api.AlipayClient;
import top.zsmile.pay.properties.AliPayConfigProperties;

/**
 * 支付宝存储
 */
public class AliStorage {


    /**
     * 配置
     */
    private AliPayConfigProperties properties;

    /**
     * 客户端
     */
    private AlipayClient client;

    public AliPayConfigProperties getProperties() {
        return properties;
    }

    public void setProperties(AliPayConfigProperties properties) {
        this.properties = properties;
    }

    public AlipayClient getClient() {
        return client;
    }

    public void setClient(AlipayClient client) {
        this.client = client;
    }
}
