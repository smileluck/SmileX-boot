package top.zsmile.tool.pay.factory;

import top.zsmile.tool.pay.enums.SignTypeEnum;
import top.zsmile.tool.pay.wechat.v2.WxPayCore;
import top.zsmile.tool.pay.wechat.v2.config.WxPayConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WxPayCoreFactory {

    private static final Map<String, WxPayCore> map = new ConcurrentHashMap<>();

    public static WxPayCore get(String key) {
        return map.get(key);
    }

    public static void put(String key, WxPayConfig config, SignTypeEnum signType, boolean useSandbox){

    }

}
