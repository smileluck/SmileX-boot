package top.zsmile.tool.wechat.mp.builder;

import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpOutVoiceMessage;


/**
 * 公众后语音消息
 */
public class VoiceBuilder extends AbstractBuilder<VoiceBuilder, WechatMpOutVoiceMessage> {

    private String voice;

    public VoiceBuilder(WechatMpInMessage message) {
        super(message);
    }

    public VoiceBuilder voice(String voice) {
        this.voice = voice;
        return this;
    }

    @Override
    protected WechatMpOutVoiceMessage createMessage() {
        return new WechatMpOutVoiceMessage();
    }

    @Override
    protected void customBuild(WechatMpOutVoiceMessage outMessage) {
        outMessage.setVoice(voice);
    }
}
