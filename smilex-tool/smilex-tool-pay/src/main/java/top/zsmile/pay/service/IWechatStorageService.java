package top.zsmile.pay.service;

import top.zsmile.pay.bean.WxV3Storage;
import top.zsmile.pay.properties.WechatPayV3Properties;

public interface IWechatStorageService {

    /**
     * 保存配置，不生成Config
     *
     * @param wechatPayV3Properties 配置信息
     */
    void save(WechatPayV3Properties wechatPayV3Properties);

    /**
     * 获取配置
     *
     * @param id 唯一ID
     * @return 配置信息
     */
    WechatPayV3Properties get(String id);

    /**
     * 保存配置，并生成Config
     *
     * @param wechatPayV3Properties 配置
     */
    void register(WechatPayV3Properties wechatPayV3Properties);

    /**
     * 获取配置
     *
     * @param id 配置ID
     * @return 配置
     */
    WxV3Storage getConfig(String id);

    /**
     * 消息防重复
     *
     * @param id id
     */
    boolean repeatNotify(String id);

    /**
     * 保存订单状态
     *
     * @param id     订单ID
     * @param statue 订单状态
     */
    void saveTransactionStatus(String id, String statue);

    /**
     * 获取订单状态
     *
     * @param id 订单ID
     * @return 订单状态
     */
    String getTransactionStatus(String id);


    /**
     * 根据appid查找配置id
     *
     * @param appid
     */
    String getByAppId(String appid);
}
