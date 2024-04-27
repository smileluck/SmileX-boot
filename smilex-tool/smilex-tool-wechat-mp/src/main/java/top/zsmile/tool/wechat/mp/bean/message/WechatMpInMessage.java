package top.zsmile.tool.wechat.mp.bean.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import top.zsmile.tool.wechat.mp.constant.WechatConstant;
import top.zsmile.tool.wechat.mp.converter.XStreamCDataConverter;
import top.zsmile.tool.wechat.mp.utils.xml.XStreamTransformer;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 微信公众号文字消息
 */
@XStreamAlias("xml")
@JacksonXmlRootElement(localName = "xml")
@XmlRootElement(name = "xml")
public class WechatMpInMessage implements Serializable {

    private static final long serialVersionUID = 1L;


    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "ToUserName")
    @JacksonXmlCData
    private String toUser;

    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "FromUserName")
    @JacksonXmlCData
    private String fromUser;

    @XStreamAlias("CreateTime")
    @JacksonXmlProperty(localName = "CreateTime")
    private Long createTime;

    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "MsgType")
    @JacksonXmlCData
    private String msgType;


    @XStreamAlias("MsgId")
    @JacksonXmlProperty(localName = "MsgId")
    private Long msgId;

    @XStreamAlias("MsgDataId")
    @JacksonXmlProperty(localName = "MsgDataId")
    private Long msgDataId;

    @XStreamAlias("Idx")
    @JacksonXmlProperty(localName = "Idx")
    private Long idx;

    @XStreamAlias("Content")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "Content")
    @JacksonXmlCData
    private String content;

    @XStreamAlias("PicUrl")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "PicUrl")
    @JacksonXmlCData
    private String picUrl;

    @XStreamAlias("MediaId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "MediaId")
    @JacksonXmlCData
    private String mediaId;

    @XStreamAlias("Event")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "Event")
    @JacksonXmlCData
    private String event;

    @XStreamAlias("EventKey")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "EventKey")
    @JacksonXmlCData
    private String eventKey;

    @XStreamAlias("Ticket")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "Ticket")
    @JacksonXmlCData
    private String ticket;


    @XStreamAlias("Latitude")
    @JacksonXmlProperty(localName = "Latitude")
    private Double latitude;

    @XStreamAlias("Longitude")
    @JacksonXmlProperty(localName = "Longitude")
    private Double longitude;

    @XStreamAlias("Precision")
    @JacksonXmlProperty(localName = "Precision")
    private Double precision;

    @XStreamAlias("Title")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "Title")
    @JacksonXmlCData
    private String title;

    @XStreamAlias("Description")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "Description")
    @JacksonXmlCData
    private String description;

    @XStreamAlias("Url")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "Url")
    @JacksonXmlCData
    private String url;


    @XStreamAlias("Location_X")
    @JacksonXmlProperty(localName = "Location_X")
    private Double locationX;

    @XStreamAlias("Location_Y")
    @JacksonXmlProperty(localName = "Location_Y")
    private Double locationY;

    @XStreamAlias("Scale")
    @JacksonXmlProperty(localName = "Scale")
    private Double scale;

    @XStreamAlias("Label")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "Label")
    @JacksonXmlCData
    private String label;


    @XStreamAlias("ThumbMediaId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "ThumbMediaId")
    @JacksonXmlCData
    private String thumbMediaId;

    @XStreamAlias("Format")
    @XStreamConverter(value = XStreamCDataConverter.class)
    @JacksonXmlProperty(localName = "Format")
    @JacksonXmlCData
    private String format;

    @XStreamAlias("Recognition")
    @JacksonXmlProperty(localName = "Recognition")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String recognition;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getLocationX() {
        return locationX;
    }

    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    public Double getLocationY() {
        return locationY;
    }

    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    public String getContent() {
        return content;
    }


    @XmlElement(name = "Content")
    public void setContent(String content) {
        this.content = content;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getMsgDataId() {
        return msgDataId;
    }

    public void setMsgDataId(Long msgDataId) {
        this.msgDataId = msgDataId;
    }

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public static WechatMpInMessage fromXML(String xml) {
        return XStreamTransformer.fromXML(WechatMpInMessage.class, xml);
    }

    @Override
    public String toString() {
        return "WechatMpInMessage{" +
                "toUser='" + toUser + '\'' +
                ", fromUser='" + fromUser + '\'' +
                ", createTime=" + createTime +
                ", msgType='" + msgType + '\'' +
                ", msgId=" + msgId +
                ", msgDataId=" + msgDataId +
                ", idx=" + idx +
                ", content='" + content + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", event='" + event + '\'' +
                ", eventKey='" + eventKey + '\'' +
                ", ticket='" + ticket + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", precision=" + precision +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                ", scale=" + scale +
                ", label='" + label + '\'' +
                ", thumbMediaId='" + thumbMediaId + '\'' +
                ", format='" + format + '\'' +
                ", recognition='" + recognition + '\'' +
                '}';
    }
}
