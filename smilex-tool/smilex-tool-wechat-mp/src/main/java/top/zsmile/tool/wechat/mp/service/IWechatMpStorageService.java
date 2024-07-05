package top.zsmile.tool.wechat.mp.service;

import top.zsmile.tool.wechat.mp.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mp.bean.WechatJsapiTicketRes;
import top.zsmile.tool.wechat.mp.bean.WechatMpQrcodeRes;
import top.zsmile.tool.wechat.mp.bean.WechatNotifyParams;
import top.zsmile.tool.wechat.mp.properties.WechatMpProperties;

import java.util.Map;

/**
 * 微信存储服务
 */
public interface IWechatMpStorageService {

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
    WechatMpProperties getDefaultWechatMp();

    /**
     * 根据AppId,获取微信公众号信息
     *
     * @param appId 微信公众号AppID
     * @return
     */
    WechatMpProperties getWechatMp(String appId);

    /**
     * 获取微信公众号配置信息
     *
     * @return
     */
    Map<String, WechatMpProperties> getWechatMpMap();

    /**
     * 根据AppId,获取微信公众号信息,不允许为空
     *
     * @param appId 微信公众号AppID
     * @return
     */
    WechatMpProperties getWechatMpNotNull(String appId);

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
    void putWechatMp(WechatMpProperties wechatProperties);

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

    /**
     * 设置微信公众号 JsapiTicket
     *
     * @param appId                 公众号 Appid
     * @param jsapiTicketProperties 公众号 JsapiTicket
     * @return 返回AccessToken对象
     */
    void putJsapiTicket(String appId, WechatJsapiTicketRes jsapiTicketProperties);

    /**
     * 获取微信公众号 JsapiTicket
     *
     * @param appId 公众号 Appid
     */
    WechatJsapiTicketRes getJsapiTicket(String appId);

    /**
     * 获取微信公众号 JsapiTicket
     *
     * @param appId 公众号 Appid
     */
    WechatJsapiTicketRes getJsapiTicketNotNull(String appId);

    /**
     * fromXml
     *
     * @param appId 公众号 appId
     * @param xml   内容
     * @return 接收消息
     */
//    WechatMpInMessage fromXml(String appId, String xml);

    /**
     * 设置重复请求
     *
     * @param params 参数
     * @return
     */
    boolean setNXRepeat(WechatNotifyParams params);

    /**
     * 设置 二维码状态
     *
     * @param qrcode 二维码信息
     * @param status 状态
     */
    void setQrStatus(WechatMpQrcodeRes qrcode, Integer status);

    /**
     * 设置 二维码状态
     *
     * @param qrcode   二维码信息
     * @param status   状态
     * @param expireIn 过期时间
     */
    void setQrStatus(WechatMpQrcodeRes qrcode, Integer status, Integer expireIn);


    /**
     * 获取 二维码状态
     *
     * @param searchId 查询ID
     */
    WechatMpQrcodeRes getQrStatus(String searchId);
}
