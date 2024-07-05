package top.zsmile.tool.wechat.mini.service;

import top.zsmile.tool.wechat.mini.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mini.properties.WechatMiniProperties;

import java.util.Map;

/**
 * 微信存储服务
 */
public interface IWechatMiniStorageService {

    /**
     * 获取默认公众号ID
     *
     * @return
     */
    String getDefaultAppId();

    /**
     * 设置默认公众号ID
     */
    void setDefaultAppId(String appId);

    /**
     * 获取默认微信公众号信息
     *
     * @return
     */
    WechatMiniProperties getDefaultWechatMp();

    /**
     * 根据AppId,获取微信公众号信息
     *
     * @param appId 微信公众号AppID
     * @return
     */
    WechatMiniProperties getWechatMp(String appId);

    /**
     * 获取微信公众号配置信息
     *
     * @return
     */
    Map<String, WechatMiniProperties> getWechatMpMap();

    /**
     * 根据AppId,获取微信公众号信息,不允许为空
     *
     * @param appId 微信公众号AppID
     * @return
     */
    WechatMiniProperties getWechatMpNotNull(String appId);

    /**
     * 根据AppId，检查微信公众号是否存在
     *
     * @param appId 微信公众号AppID
     * @return
     */
    boolean checkWechatMp(String appId);


    /**
     * 清空微信公众号信息
     */
    void clearWechatMp();

    /**
     * 添加微信公众号信息
     *
     * @param wechatProperties 微信公众号信息
     */
    void putWechatMp(WechatMiniProperties wechatProperties);

    /**
     * 设置微信公众号 AccessToken
     *
     * @param appId           公众号 Appid
     * @param wechatATokenRes 公众号token信息
     * @return 返回AccessToken对象
     */
    void putAccessToken(String appId, WechatATokenRes wechatATokenRes);

    /**
     * 获取微信公众号 AccessToken
     *
     * @param appId 公众号 Appid
     */
    WechatATokenRes getAccessToken(String appId);

    /**
     * 刷新微信公众号 AccessToken
     */
    Map<String, WechatATokenRes> getAccessTokenMap();

}
