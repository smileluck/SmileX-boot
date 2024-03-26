package top.zsmile.tool.wechat.mp.service;

import top.zsmile.tool.wechat.mp.bean.WechatNotifyParams;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;
import top.zsmile.tool.wechat.mp.exceptions.AesException;
import top.zsmile.tool.wechat.mp.utils.WXBizMsgCrypt;

/**
 * 加密和解密
 */
public interface IWechatCryptoService {

    WechatMpInMessage decrypt(String appId, WechatNotifyParams params, String postData);

    WechatMpInMessage decrypt(String appId, String msgSignature, String timeStamp, String nonce, String postData);

    String encrypt(String appId, String replyXml, String timeStamp, String nonce);

    WXBizMsgCrypt register(String appId) throws AesException;
}
