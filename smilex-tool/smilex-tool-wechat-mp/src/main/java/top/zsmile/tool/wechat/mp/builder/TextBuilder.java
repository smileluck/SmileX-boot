package top.zsmile.tool.wechat.mp.builder;

import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpOutTextMessage;


/**
 * 公众后文本消息消息
 */
public class TextBuilder extends AbstractBuilder<TextBuilder, WechatMpOutTextMessage> {

    private String content;

    public TextBuilder content(String content) {
        this.content = content;
        return this;
    }

    public TextBuilder(WechatMpInMessage message) {
        super(message);
    }

    @Override
    protected WechatMpOutTextMessage createMessage() {
        return new WechatMpOutTextMessage();
    }

    @Override
    protected void customBuild(WechatMpOutTextMessage outMessage) {
        outMessage.setContent(this.content);
    }
}
