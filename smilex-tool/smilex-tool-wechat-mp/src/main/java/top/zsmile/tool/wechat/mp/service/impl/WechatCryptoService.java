package top.zsmile.tool.wechat.mp.service.impl;

import com.ruoyi.wx.mp.bean.WechatNotifyParams;
import com.ruoyi.wx.mp.bean.message.WechatMpInMessage;
import com.ruoyi.wx.mp.properties.WechatMpProperties;
import com.ruoyi.wx.mp.service.IWechatCryptoService;
import com.ruoyi.wx.mp.service.IWechatStorageService;
import com.ruoyi.wx.mp.utils.WXBizMsgCrypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class WechatCryptoService implements IWechatCryptoService {

    private static final ConcurrentHashMap<String, WXBizMsgCrypt> CRYPTO_MAP = new ConcurrentHashMap<>();

    @Resource
    private IWechatStorageService wechatStorageService;

    @Override
    public WechatMpInMessage decrypt(String appId, WechatNotifyParams params, String postData) {
        return decrypt(appId, params.getMsg_signature(), params.getTimestamp(), params.getNonce(), postData);
    }

    @Override
    public WechatMpInMessage decrypt(String appId, String msgSignature, String timeStamp, String nonce, String postData) {
        WXBizMsgCrypt wxBizMsgCrypt = get(appId);
        String xml = wxBizMsgCrypt.decryptMsg(msgSignature, timeStamp, nonce, postData);
        if (StringUtils.isNotBlank(xml)) {
            return WechatMpInMessage.fromXML(xml);
        }
        return null;
    }

    @Override
    public String encrypt(String appId, String replyXml, String timeStamp, String nonce) {
        WXBizMsgCrypt wxBizMsgCrypt = get(appId);
        return wxBizMsgCrypt.encryptMsg(replyXml, timeStamp, nonce);
    }

    private WXBizMsgCrypt get(String appId) {
        WXBizMsgCrypt wxBizMsgCrypt = CRYPTO_MAP.get(appId);
        if (wxBizMsgCrypt == null) {
            wxBizMsgCrypt = register(appId);
        }
        return wxBizMsgCrypt;
    }

    @Override
    public WXBizMsgCrypt register(String appId) {
        WechatMpProperties wechatMp = wechatStorageService.getWechatMpNotNull(appId);
        WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(wechatMp.getToken(), wechatMp.getAeskey(), wechatMp.getAppId());
        CRYPTO_MAP.put(appId, wxBizMsgCrypt);
        return wxBizMsgCrypt;
    }
}
