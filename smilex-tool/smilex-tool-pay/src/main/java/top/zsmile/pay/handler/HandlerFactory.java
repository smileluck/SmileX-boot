package top.zsmile.pay.handler;

import com.wechat.pay.java.service.payments.model.Transaction;

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
        tradeDefaultHandler.exec(transaction);
    }

    public static AbstractHandler get(String key) {
        return HANDLE_MAP.get(key);
    }

    public static boolean support(String key) {
        return HANDLE_MAP.keySet().contains(key);
    }
}
