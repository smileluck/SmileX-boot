package top.zsmile.tool.wechat.mp.bean.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import top.zsmile.tool.wechat.mp.converter.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import java.io.Serializable;

/**
 * 微信输出XMl
 */
@XStreamAlias("xml")
@JacksonXmlRootElement(localName = "xml")
public class WechatMpOutMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    public WechatMpOutMessage(String type) {
        this.msgType = type;
    }

    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "ToUserName")
    @JacksonXmlCData
    protected String toUser;

    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "FromUserName")
    @JacksonXmlCData
    protected String fromUser;

    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "MsgType")
    @JacksonXmlCData
    private String msgType;

    @XStreamAlias("CreateTime")
    @JacksonXmlProperty(localName = "CreateTime")
    private Long createTime;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
