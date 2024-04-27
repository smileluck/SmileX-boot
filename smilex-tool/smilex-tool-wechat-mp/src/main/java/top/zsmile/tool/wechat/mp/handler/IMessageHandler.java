package top.zsmile.tool.wechat.mp.handler;

public interface IMessageHandler<T> {

    /**
     * 处理接入信息
     *
     * @param messageInHandler 微信POJO消息
     */
    void execIn(T messageInHandler);

    /**
     * 处理接入信息
     *
     * @param xml 微信XML消息
     */
    void execIn(String xml);
}
