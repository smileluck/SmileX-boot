package top.zsmile.pay.factory;

import top.zsmile.common.utils.Asserts;
import top.zsmile.pay.wechat.v2.handler.BaseHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PayTradeTypeFactory {

    private static Map<String, BaseHandler> services = new ConcurrentHashMap<String, BaseHandler>();

    public static BaseHandler get(String type) {
        return services.get(type);
    }

    public static void register(String type, BaseHandler baseHandler) {
        Asserts.isNotNull(type, "type can't be null");
        Asserts.isNotNull(baseHandler, "handler can't be null");
        services.put(type, baseHandler);
    }
}
