package top.zsmile.pay.service;

import top.zsmile.pay.bean.AliStorage;
import top.zsmile.pay.properties.AliPayConfigProperties;

/**
 * 支付宝 存储服务
 */
public interface IAliStorageService {

    /**
     * 保存配置，并生成客户端
     *
     * @param properties 配置信息
     */
    void save(AliPayConfigProperties properties);

    /**
     * 获取客户端
     *
     * @param id 唯一ID
     * @return 请求客户端
     */
    AliStorage get(String id);

    /**
     * 消息防重复
     *
     * @param id id
     */
    boolean repeatNotify(String id);
}
