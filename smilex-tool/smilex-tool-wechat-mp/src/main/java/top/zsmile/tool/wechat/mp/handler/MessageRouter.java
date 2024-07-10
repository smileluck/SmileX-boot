package top.zsmile.tool.wechat.mp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zsmile.common.thread.factory.BaseThreadFactory;
import top.zsmile.common.thread.utils.Threads;
import top.zsmile.tool.wechat.mp.bean.message.WechatMpInMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 消息处理
 */
public class MessageRouter {
    private static final Logger log = LoggerFactory.getLogger(MessageRouter.class);

    private static final List<MessageRouterRule> RULES = new ArrayList<>();

    /**
     * 线程池
     */
    private static ExecutorService executorService;


    static {
        BaseThreadFactory.Builder builder = new BaseThreadFactory.Builder();
        builder.prefix("Global-ThreadPool-");
        builder.uncaughtExceptionHandler((t, err) -> {
            log.error("threadName: {} , err => {}", t.getName(), err.getMessage());
        });
        executorService = new ThreadPoolExecutor(50,
                50,
                0,
                TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(), builder.build());
    }

    private MessageRouter() {
    }

    public static void shutDownExecutorService() {
        Threads.shutdownAndAwaitTermination(executorService);
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
            Future<String> submit = executorService.submit(() -> match.getHandler().exec(openid, message));
            try {
                return submit.get(5, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }
}
