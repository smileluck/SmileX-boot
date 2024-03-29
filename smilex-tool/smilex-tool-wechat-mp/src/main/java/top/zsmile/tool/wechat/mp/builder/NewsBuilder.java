package top.zsmile.tool.wechat.mp.builder;

import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpOutNewsMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 公众号图文消息
 */
public class NewsBuilder extends AbstractBuilder<NewsBuilder, WechatMpOutNewsMessage> {
    private List<WechatMpOutNewsMessage.Item> articles = new ArrayList<>();

    public NewsBuilder(WechatMpInMessage message) {
        super(message);
    }

    public NewsBuilder addArticle(WechatMpOutNewsMessage.Item... items) {
        Collections.addAll(this.articles, items);
        return this;
    }

    public NewsBuilder articles(List<WechatMpOutNewsMessage.Item> articles) {
        this.articles = articles;
        return this;
    }

    @Override
    protected WechatMpOutNewsMessage createMessage() {
        return new WechatMpOutNewsMessage();
    }

    @Override
    protected void customBuild(WechatMpOutNewsMessage outMessage) {
        for (WechatMpOutNewsMessage.Item item : this.articles) {
            outMessage.addArticle(item);
        }
    }
}
