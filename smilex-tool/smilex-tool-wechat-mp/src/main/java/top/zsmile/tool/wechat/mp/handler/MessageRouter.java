package top.zsmile.tool.wechat.mp.handler;

import com.ruoyi.wx.mp.bean.message.WechatMpInMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息处理
 */
public class MessageRouter {

    private static final List<MessageRouterRule> RULES = new ArrayList<>();

    private MessageRouter() {
    }

    public static MessageRouterRule rule() {
        MessageRouterRule messageRouterRule = new MessageRouterRule();
        RULES.add(messageRouterRule);
        return messageRouterRule;
    }

    public static List<MessageRouterRule> matches(WechatMpInMessage message) {
        return RULES.stream()
                .filter(rule -> rule.matches(message))
                .collect(Collectors.toList());
    }

    public static String exec(String openid, WechatMpInMessage message) {
        List<MessageRouterRule> matches = matches(message);
        for (MessageRouterRule match : matches) {
            return match.getHandler().exec(openid,message);
        }
        return null;
    }
}
