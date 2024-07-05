package top.zsmile.tool.wechat.mini.service;


import top.zsmile.tool.wechat.mini.bean.WechatATokenRes;
import top.zsmile.tool.wechat.mini.bean.WechatMiniOpenid;
import top.zsmile.tool.wechat.mini.bean.WechatMiniPhoneInfo;

public interface IWechatMiniService {


    /**
     * 检查公众号配置
     *
     * @param appId 公众号AppId
     */
    boolean checkConfig(String appId);

    /**
     * 获取AccessToken
     *
     * @return
     */
    WechatATokenRes getAccessToken(String appId);

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
     * 获取手机号验证
     *
     * @return 手机号信息
     */
    WechatMiniPhoneInfo getPhoneNumber(String code);

    /**
     * 获取手机号验证
     *
     * @param appId appId
     * @return 手机号信息
     */
    WechatMiniPhoneInfo getPhoneNumber(String appId, String code);


    /**
     * 获取openid和unionid
     *
     * @param code
     * @return
     */
    WechatMiniOpenid code2Session(String code);

    /**
     * 获取openid和unionid
     *
     * @param appId appId
     * @param code  code
     * @return 返回openid
     */
    WechatMiniOpenid code2Session(String appId, String code);
}
