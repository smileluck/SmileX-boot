package top.zsmile.tool.wechat.mp.bean.message;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import top.zsmile.tool.wechat.mp.constant.WechatConstant;
import top.zsmile.tool.wechat.mp.converter.XStreamMediaIdConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * 微信图文信息
 */
@XStreamAlias("xml")
@JacksonXmlRootElement(localName = "xml")
public class WechatMpOutImageMessage extends WechatMpOutMessage {

    public WechatMpOutImageMessage() {
        super(WechatConstant.XmlMsgType.IMAGE);
    }
    @XStreamAlias("Image")
    @XStreamConverter(value = XStreamMediaIdConverter.class)
    @JacksonXmlProperty(localName = "Image")
    @JacksonXmlCData
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
