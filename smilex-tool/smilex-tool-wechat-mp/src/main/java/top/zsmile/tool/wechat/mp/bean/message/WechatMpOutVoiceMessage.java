package top.zsmile.tool.wechat.mp.bean.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import top.zsmile.tool.wechat.mp.constant.WechatConstant;
import top.zsmile.tool.wechat.mp.converter.XStreamMediaIdConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * 微信公众号语音消息
 */
@XStreamAlias("xml")
@JacksonXmlRootElement(localName = "xml")
public class WechatMpOutVoiceMessage extends WechatMpOutMessage {

    public WechatMpOutVoiceMessage() {
        super(WechatConstant.XmlMsgType.VOICE);
    }

    @XStreamAlias("Voice")
    @XStreamConverter(value = XStreamMediaIdConverter.class)
    @JacksonXmlProperty(localName = "Voice")
    @JacksonXmlCData
    private String voice;

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }
}
