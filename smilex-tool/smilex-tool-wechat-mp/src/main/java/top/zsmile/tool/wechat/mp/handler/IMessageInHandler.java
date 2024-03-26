package top.zsmile.tool.wechat.mp.handler;

import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;

public interface IMessageInHandler {

    /**
     * 处理接入信息
     *
     * @param openid            微信公众号id
     * @param wechatMpInMessage 微信接收的消息
     */
    String exec(String openid, WechatMpInMessage wechatMpInMessage);
}
