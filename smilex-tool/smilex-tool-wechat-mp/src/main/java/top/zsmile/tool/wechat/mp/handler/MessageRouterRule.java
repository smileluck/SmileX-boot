package top.zsmile.tool.wechat.mp.handler;

import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;

/**
 * 消息路由规则
 */
public class MessageRouterRule implements IMessageRule<WechatMpInMessage> {

    private String toUser;

    private String fromUser;

    private String msgType;

    private String event;

    private String eventKey;

    private IMessageInHandler handler;

    public String getToUser() {
        return toUser;
    }

    public MessageRouterRule setToUser(String toUser) {
        this.toUser = toUser;
        return this;
    }

    public String getFromUser() {
        return fromUser;
    }

    public MessageRouterRule setFromUser(String fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    public String getMsgType() {
        return msgType;
    }

    public MessageRouterRule setMsgType(String msgType) {
        this.msgType = msgType;
        return this;
    }

    public String getEvent() {
        return event;
    }

    public MessageRouterRule setEvent(String event) {
        this.event = event;
        return this;
    }

    public String getEventKey() {
        return eventKey;
    }

    public MessageRouterRule setEventKey(String eventKey) {
        this.eventKey = eventKey;
        return this;
    }

    @Override
    public boolean matches(WechatMpInMessage wxMessage) {
        return (this.toUser == null || this.toUser.equals(wxMessage.getFromUser()))
                &&
                (this.fromUser == null || this.fromUser.equals(wxMessage.getFromUser()))
                &&
                (this.msgType == null || this.msgType.equalsIgnoreCase(wxMessage.getMsgType()))
                &&
                (this.event == null || this.event.equalsIgnoreCase(wxMessage.getEvent()))
                &&
                (this.eventKey == null || this.eventKey.equalsIgnoreCase(wxMessage.getEventKey()));
    }

    public IMessageInHandler getHandler() {
        return handler;
    }

    public MessageRouterRule setHandler(IMessageInHandler handler) {
        this.handler = handler;
        return this;
    }
}
