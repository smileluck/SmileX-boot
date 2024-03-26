package top.zsmile.tool.wechat.mp.service;

import com.ruoyi.wx.mp.bean.WechatNotifyParams;
import com.ruoyi.wx.mp.bean.message.WechatMpInMessage;
import com.ruoyi.wx.mp.exceptions.AesException;
import com.ruoyi.wx.mp.utils.WXBizMsgCrypt;

/**
 * 加密和解密
 */
public interface IWechatCryptoService {

    WechatMpInMessage decrypt(String appId, WechatNotifyParams params, String postData);

    WechatMpInMessage decrypt(String appId, String msgSignature, String timeStamp, String nonce, String postData);

    String encrypt(String appId, String replyXml, String timeStamp, String nonce);

    WXBizMsgCrypt register(String appId) throws AesException;
}
