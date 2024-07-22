package top.zsmile.pay.handler;

import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.RefundNotification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理工厂
 */
public class HandlerFactory {

    private static TradeDefaultHandler tradeDefaultHandler = null;

    private final static ConcurrentHashMap<String, AbstractHandler> HANDLE_MAP = new ConcurrentHashMap<>();

    public static void init(TradeDefaultHandler handler) {
        tradeDefaultHandler = handler;
    }

    public static void register(String key, AbstractHandler handler) {
        HANDLE_MAP.put(key, handler);
    }

    public static void exec(Transaction transaction) {
        tradeDefaultHandler.wxExec(transaction);
    }

    public static void exec(Map<String, String> transaction) {
        tradeDefaultHandler.aliExec(transaction);
    }
    public static void execRefund(RefundNotification refundNotification) {
        tradeDefaultHandler.wxExecRefund(refundNotification);
    }
    public static AbstractHandler get(String key) {
        return HANDLE_MAP.get(key);
    }

    public static boolean support(String key) {
        return HANDLE_MAP.keySet().contains(key);
    }
}
