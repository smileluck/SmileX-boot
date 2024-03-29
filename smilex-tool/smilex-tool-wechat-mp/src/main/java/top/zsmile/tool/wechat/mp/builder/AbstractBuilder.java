package top.zsmile.tool.wechat.mp.builder;

import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpOutMessage;
import top.zsmile.tool.wechat.mp.utils.xml.XStreamTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 公众号消息抽象类
 *
 * @param <T> 生成器类型
 * @param <W> 生成对象类
 */
public abstract class AbstractBuilder<T extends AbstractBuilder, W extends WechatMpOutMessage> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String toUser;

    protected String fromUser;

    protected WechatMpInMessage inMessage;

    protected W outMessage;

    public T toUser(String toUser) {
        this.toUser = toUser;
        return (T) this;
    }

    public T fromUser(String fromUser) {
        this.fromUser = fromUser;
        return (T) this;
    }

    public AbstractBuilder(WechatMpInMessage message) {
        this.inMessage = message;
        this.toUser = message.getFromUser();
        this.fromUser = message.getToUser();
    }

    protected abstract W createMessage();

    protected abstract void customBuild(W outMessage);

    /**
     * 生成对象
     *
     * @return <W>对象
     */
    public W build() {
        this.outMessage = createMessage();
        this.outMessage.setToUser(this.toUser);
        this.outMessage.setFromUser(this.fromUser);
        this.outMessage.setCreateTime(System.currentTimeMillis() / 1000L);
        customBuild(this.outMessage);
        return this.outMessage;
    }

    /**
     * 生成XML
     *
     * @return 明文XML
     */
    public String buildXml() {
        W obj = this.build();
        return XStreamTransformer.toXML(obj.getClass(), obj);
    }

}
