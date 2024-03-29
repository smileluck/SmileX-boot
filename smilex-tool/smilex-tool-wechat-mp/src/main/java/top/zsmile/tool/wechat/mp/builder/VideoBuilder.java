package top.zsmile.tool.wechat.mp.builder;

import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpOutVideoMessage;


/**
 * 公众后视频消息
 */
public class VideoBuilder extends AbstractBuilder<VideoBuilder, WechatMpOutVideoMessage> {

    private String mediaId;
    private String title;
    private String description;

    public VideoBuilder(WechatMpInMessage message) {
        super(message);
    }

    public VideoBuilder title(String title) {
        this.title = title;
        return this;
    }

    public VideoBuilder description(String description) {
        this.description = description;
        return this;
    }

    public VideoBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }
    @Override
    protected WechatMpOutVideoMessage createMessage() {
        return new WechatMpOutVideoMessage();
    }

    @Override
    protected void customBuild(WechatMpOutVideoMessage outMessage) {
        outMessage.setTitle(title);
        outMessage.setDescription(description);
        outMessage.setMediaId(mediaId);
    }
}
