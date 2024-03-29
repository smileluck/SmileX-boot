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
 * 微信公众号音乐消息
 */
@XStreamAlias("xml")
@JacksonXmlRootElement(localName = "xml")
public class WechatMpOutMusicMessage extends WechatMpOutMessage {

    @XStreamAlias("Music")
    @JacksonXmlProperty(localName = "Music")
    protected final Music music = new Music();

    public WechatMpOutMusicMessage() {
        super(WechatConstant.XmlMsgType.MUSIC);
    }

    @XStreamAlias("Music")
    @JacksonXmlRootElement(localName = "Music")
    public static class Music implements Serializable {
        private static final long serialVersionUID = -5492592401691895334L;

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

        @XStreamAlias("ThumbMediaId")
        @XStreamConverter(value = XStreamCDataConverter.class)
        @JacksonXmlProperty(localName = "ThumbMediaId")
        @JacksonXmlCData
        private String thumbMediaId;

        @XStreamAlias("MusicUrl")
        @XStreamConverter(value = XStreamCDataConverter.class)
        @JacksonXmlProperty(localName = "MusicUrl")
        @JacksonXmlCData
        private String musicUrl;

        @XStreamAlias("HQMusicUrl")
        @XStreamConverter(value = XStreamCDataConverter.class)
        @JacksonXmlProperty(localName = "HQMusicUrl")
        @JacksonXmlCData
        private String hqMusicUrl;

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

        public String getThumbMediaId() {
            return thumbMediaId;
        }

        public void setThumbMediaId(String thumbMediaId) {
            this.thumbMediaId = thumbMediaId;
        }

        public String getMusicUrl() {
            return musicUrl;
        }

        public void setMusicUrl(String musicUrl) {
            this.musicUrl = musicUrl;
        }

        public String getHqMusicUrl() {
            return hqMusicUrl;
        }

        public void setHqMusicUrl(String hqMusicUrl) {
            this.hqMusicUrl = hqMusicUrl;
        }
    }

    public String getTitle() {
        return this.music.title;
    }

    public void setTitle(String title) {
        this.music.title = title;
    }

    public String getDescription() {
        return this.music.description;
    }

    public void setDescription(String description) {
        this.music.description = description;
    }

    public String getThumbMediaId() {
        return this.music.thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.music.thumbMediaId = thumbMediaId;
    }

    public String getMusicUrl() {
        return this.music.musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.music.musicUrl = musicUrl;
    }

    public String getHqMusicUrl() {
        return this.music.hqMusicUrl;
    }

    public void setHqMusicUrl(String hqMusicUrl) {
        this.music.hqMusicUrl = hqMusicUrl;
    }
}
