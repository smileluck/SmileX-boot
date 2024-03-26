package top.zsmile.tool.wechat.mp.handler.in;


import com.ruoyi.wx.mp.bean.message.WechatMpInMessage;
import com.ruoyi.wx.mp.constant.WechatConstant;
import com.ruoyi.wx.mp.handler.AbstractInHandler;
import com.ruoyi.wx.mp.handler.IMessageInHandler;
import com.ruoyi.wx.mp.handler.MessageRouter;
import com.ruoyi.wx.mp.service.IWechatStorageService;

import javax.annotation.Resource;

//@Component
public class MessageInScanHandler extends AbstractInHandler implements IMessageInHandler {

    @Resource
    private IWechatStorageService wechatStorageService;

    @Override
    public String exec(String openid, WechatMpInMessage wechatMpInMessage) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageRouter.rule().setEvent(WechatConstant.XmlMsgType.EVENT).setEvent(WechatConstant.XmlEventType.SCAN).setHandler(this);
        MessageRouter.rule().setEvent(WechatConstant.XmlMsgType.EVENT).setEvent(WechatConstant.XmlEventType.SUBSCRIBE).setHandler(this);
    }
}
