package top.zsmile.tool.wechat.mp.service;

import top.zsmile.tool.wechat.mp.bean.*;
import top.zsmile.tool.wechat.mp.constant.WechatConstant;

public interface IWechatMpService {


    /**
     * 检查公众号配置
     *
     * @param appId 公众号AppId
     */
    boolean checkConfig(String appId);

    /**
     * 检查签名
     *
     * @param params 请求参数
     * @return
     */
    boolean checkSignature(WechatNotifyParams params);

    /**
     * 检查签名
     *
     * @param params 请求参数
     * @return
     */
    boolean checkSignature(WechatConfigParams params);

    /**
     * 检查签名
     *
     * @param signature 签名信息
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @return
     */
    boolean checkSignature(String signature, String timestamp, String nonce);

    /**
     * 检查签名
     *
     * @param appId     公众号AppId
     * @param signature 签名信息
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @return
     */
    boolean checkSignature(String appId, String signature, String timestamp, String nonce);

    /**
     * 获取AccessToken
     *
     * @return
     */
    WechatATokenRes getAccessToken(String appId);


    /**
     * 获取JsapiTicket
     *
     * @return
     */
    WechatJsapiTicketRes getJsapiTicket(String appId);


    /**
     * 二维码，默认临时
     *
     * @param sceneId 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     * @param type    二维码类型
     * @return
     */
    WechatMpQrcodeRes createQrcode(String sceneId, WechatConstant.QrCodeType type);

    /**
     * 二维码，默认临时
     *
     * @param sceneId 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     * @param type    二维码类型
     * @param userId  用户ID
     * @return
     */
    WechatMpQrcodeRes createQrcode(String sceneId, WechatConstant.QrCodeType type, Long userId);

    /**
     * 二维码，默认临时
     *
     * @param appId   公众号ID
     * @param sceneId 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     * @param type    二维码类型
     * @param userId  用户ID
     * @return
     */
    WechatMpQrcodeRes createQrcode(String appId, String sceneId, WechatConstant.QrCodeType type, Long userId);

    /**
     * 二维码，默认临时
     *
     * @param appId    公众号ID
     * @param sceneId  场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     * @param type    二维码类型
     * @param userId  用户ID
     * @param expireIn 过期时间单位s
     * @return
     */
    WechatMpQrcodeRes createQrcode(String appId, String sceneId, WechatConstant.QrCodeType type, Long userId, Integer expireIn);


    /**
     * 二维码，默认临时
     *
     * @param appId     公众号ID
     * @param sceneId   场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     * @param type    二维码类型
     * @param userId  用户ID
     * @param expireIn  过期时间单位s
     * @param perpetual 是否永久
     * @return
     */
    WechatMpQrcodeRes createQrcode(String appId, String sceneId, WechatConstant.QrCodeType type, Long userId, Integer expireIn, boolean perpetual);

    /**
     * 二维码，默认临时
     *
     * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param type    二维码类型
     * @return
     */
    WechatMpQrcodeRes createQrcode(Integer sceneId, WechatConstant.QrCodeType type);

    /**
     * 二维码，默认临时
     *
     * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param type    二维码类型
     * @param userId  用户ID
     * @return
     */
    WechatMpQrcodeRes createQrcode(Integer sceneId, WechatConstant.QrCodeType type, Long userId);

    /**
     * 二维码，默认临时
     *
     * @param appId   公众号ID
     * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param type    二维码类型
     * @param userId  用户ID
     * @return
     */
    WechatMpQrcodeRes createQrcode(String appId, Integer sceneId, WechatConstant.QrCodeType type, Long userId);

    /**
     * 二维码，默认临时
     *
     * @param appId    公众号ID
     * @param sceneId  场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param type    二维码类型
     * @param userId  用户ID
     * @param expireIn 过期时间单位s
     * @return
     */
    WechatMpQrcodeRes createQrcode(String appId, Integer sceneId, WechatConstant.QrCodeType type, Long userId, Integer expireIn);

    /**
     * 二维码，默认临时
     *
     * @param appId    公众号ID
     * @param sceneId  场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param type    二维码类型
     * @param userId  用户ID
     * @param expireIn 过期时间单位s
     * @param perpetual 是否永久
     * @return
     */
    WechatMpQrcodeRes createQrcode(String appId, Integer sceneId, WechatConstant.QrCodeType type, Long userId, Integer expireIn, boolean perpetual);

    /**
     * 查询二维码状态
     *
     * @param searchId 二维码ID
     * @return 返回状态
     */
    WechatMpQrcodeRes getQrcodeStatus(String searchId);

    /**
     * 刷新 AccessToken
     */
    void refreshAccessToken();

    /**
     * 刷新 AccessToken
     *
     * @param appId 公众号APPID
     */
    void refreshAccessToken(String appId);

    /**
     * 重复消息
     *
     * @param params 通知参数
     * @return 是否重复
     */
    boolean repeatNotify(WechatNotifyParams params);


    /**
     * 处理消息
     *
     * @param params   通知参数
     * @param postData 数据
     * @return 响应结果
     */
    String handleMessage(WechatNotifyParams params, String postData);


    /**
     * 处理消息
     *
     * @param appId    公众号AppId
     * @param params   通知参数
     * @param postData 数据
     * @return 响应结果
     */
    String handleMessage(String appId, WechatNotifyParams params, String postData);
}
