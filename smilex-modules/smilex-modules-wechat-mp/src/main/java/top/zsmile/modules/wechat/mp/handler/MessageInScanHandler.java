package top.zsmile.modules.wechat.mp.handler;


import top.zsmile.tool.wechat.mp.bean.WechatMpQrcodeRes;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;
import top.zsmile.tool.wechat.mp.builder.TextBuilder;
import top.zsmile.tool.wechat.mp.constant.WechatMpConstant;
import top.zsmile.tool.wechat.mp.handler.AbstractInHandler;
import top.zsmile.tool.wechat.mp.handler.IMessageInHandler;
import top.zsmile.tool.wechat.mp.handler.MessageRouter;
import org.springframework.stereotype.Component;
import top.zsmile.tool.wechat.mp.service.IWechatMpStorageService;

import javax.annotation.Resource;

@Component
public class MessageInScanHandler extends AbstractInHandler implements IMessageInHandler {

    @Resource
    private IWechatMpStorageService wechatStorageService;

    @Override
    public String exec(String openid, WechatMpInMessage wechatMpInMessage) {
        try {
            WechatMpQrcodeRes qrStatus = wechatStorageService.getQrStatus(wechatMpInMessage.getTicket());
            Integer status = qrStatus.getStatus();
            if (WechatMpConstant.QrCodeStatus.LOOP.equals(status)) {
                qrStatus.setOpenid(openid);
//                TapUser user = tapUserService.lambdaQuery()
//                        .select(TapUser::getId)
//                        .eq(TapUser::getWxOpenid, openid)
//                        .last("limit 1").one();
//                if (user == null) {
//                    wechatStorageService.setQrStatus(qrStatus, WechatConstant.QrCodeStatus.NOT_REG);
//                    if (qrStatus.getType().equals(WechatConstant.QrCodeType.BIND.getType())) {
//                        tapUserService.lambdaUpdate()
//                                .eq(TapUser::getId, qrStatus.getUserId())
//                                .set(TapUser::getWxOpenid, openid)
//                                .update();
//                    }
//            } else {
//                wechatStorageService.setQrStatus(qrStatus, WechatMpConstant.QrCodeStatus.REG);
//            }
            }
            return new TextBuilder(wechatMpInMessage).content("欢迎登录塔普智能，敬请期待新功能").buildXml();
        } catch (
                Exception ex) {
            logger.error("MessageInScanHandler ex => {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageRouter.rule().setMsgType(WechatMpConstant.XmlMsgType.EVENT).setEvent(WechatMpConstant.XmlEventType.SCAN).setHandler(this);
    }
}
