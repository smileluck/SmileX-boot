package top.zsmile.tool.wechat.mp.builder;

import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpOutMusicMessage;

/**
 * 公众后音乐消息
 */
public class MusicBuilder extends AbstractBuilder<MusicBuilder, WechatMpOutMusicMessage> {

    private String title;
    private String description;
    private String hqMusicUrl;
    private String musicUrl;
    private String thumbMediaId;

    public MusicBuilder(WechatMpInMessage message) {
        super(message);
    }

    public MusicBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MusicBuilder description(String description) {
        this.description = description;
        return this;
    }

    public MusicBuilder hqMusicUrl(String hqMusicUrl) {
        this.hqMusicUrl = hqMusicUrl;
        return this;
    }

    public MusicBuilder musicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
        return this;
    }

    public MusicBuilder thumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
        return this;
    }


    @Override
    protected WechatMpOutMusicMessage createMessage() {
        return new WechatMpOutMusicMessage();
    }

    @Override
    protected void customBuild(WechatMpOutMusicMessage outMessage) {
        outMessage.setTitle(title);
        outMessage.setDescription(description);
        outMessage.setHqMusicUrl(hqMusicUrl);
        outMessage.setMusicUrl(musicUrl);
        outMessage.setThumbMediaId(thumbMediaId);
    }
}
