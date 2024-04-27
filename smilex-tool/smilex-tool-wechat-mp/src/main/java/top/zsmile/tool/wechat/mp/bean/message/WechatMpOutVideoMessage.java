package top.zsmile.tool.wechat.mp.bean.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import top.zsmile.tool.wechat.mp.constant.WechatConstant;
import top.zsmile.tool.wechat.mp.converter.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import java.io.Serializable;

/**
 * 微信公众号语音消息
 */
@XStreamAlias("xml")
@JacksonXmlRootElement(localName = "xml")
public class WechatMpOutVideoMessage extends WechatMpOutMessage {

    public WechatMpOutVideoMessage() {
        super(WechatConstant.XmlMsgType.VIDEO);
    }

    @XStreamAlias("Video")
    @JacksonXmlProperty(localName = "Video")
    protected Video video = new Video();

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @XStreamAlias("Video")
    @JacksonXmlRootElement(localName = "Video")
    public static class Video implements Serializable {
        private static final long serialVersionUID = -6445448977569651183L;

        @XStreamAlias("MediaId")
        @XStreamConverter(value = XStreamCDataConverter.class)
        @JacksonXmlProperty(localName = "MediaId")
        @JacksonXmlCData
        private String mediaId;

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

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
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
    }

    public String getMediaId() {
        return this.video.mediaId;
    }

    public void setMediaId(String mediaId) {
        this.video.mediaId = mediaId;
    }

    public String getTitle() {
        return this.video.title;
    }

    public void setTitle(String title) {
        this.video.title = title;
    }

    public String getDescription() {
        return this.video.description;
    }

    public void setDescription(String description) {
        this.video.description = description;
    }
}
