package top.zsmile.tool.wechat.mp.handler.in;


import org.springframework.stereotype.Component;
import top.zsmile.tool.wechat.mp.bean.WechatMpQrcodeRes;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;
import top.zsmile.tool.wechat.mp.constant.WechatConstant;
import top.zsmile.tool.wechat.mp.handler.AbstractInHandler;
import top.zsmile.tool.wechat.mp.handler.IMessageInHandler;
import top.zsmile.tool.wechat.mp.handler.MessageRouter;
import top.zsmile.tool.wechat.mp.service.IWechatStorageService;

import javax.annotation.Resource;

/**
 * 扫码
 */
@Component
public class MessageInScanHandler extends AbstractInHandler implements IMessageInHandler {

    @Resource
    private IWechatStorageService wechatStorageService;

    @Override
    public String exec(String openid, WechatMpInMessage wechatMpInMessage) {
        try {
            WechatMpQrcodeRes qrStatus = wechatStorageService.getQrStatus(wechatMpInMessage.getTicket());
            Integer status = qrStatus.getStatus();
            if (WechatConstant.QrCodeStatus.LOOP.equals(status)) {
                qrStatus.setOpenid(openid);
//                if (user == null) {
//                    wechatStorageService.setQrStatus(qrStatus, WechatConstant.QrCodeStatus.NOT_REG);
//                    }
//                } else {
//                    wechatStorageService.setQrStatus(qrStatus, WechatConstant.QrCodeStatus.REG);
//                }
            }
            return null;
        } catch (Exception ex) {
            logger.error("MessageInScanHandler ex => {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageRouter.rule().setEvent(WechatConstant.XmlMsgType.EVENT).setEvent(WechatConstant.XmlEventType.SCAN).setHandler(this);
        MessageRouter.rule().setEvent(WechatConstant.XmlMsgType.EVENT).setEvent(WechatConstant.XmlEventType.SUBSCRIBE).setHandler(this);
    }
}
