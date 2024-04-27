package top.zsmile.tool.wechat.mp.builder;

import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpOutImageMessage;

/**
 * 公众后图片消息
 */
public class ImageBuilder extends AbstractBuilder<ImageBuilder, WechatMpOutImageMessage> {

    private String image;

    public ImageBuilder(WechatMpInMessage message) {
        super(message);
    }

    public ImageBuilder image(String image) {
        this.image = image;
        return this;
    }

    @Override
    protected WechatMpOutImageMessage createMessage() {
        return new WechatMpOutImageMessage();
    }

    @Override
    protected void customBuild(WechatMpOutImageMessage outMessage) {
        outMessage.setImage(image);
    }
}
