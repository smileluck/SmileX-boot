package top.zsmile.pay.wechat.v2.handler;


import top.zsmile.pay.entity.vo.ReturnVO;
import top.zsmile.pay.wechat.v2.config.WxPayConfig;

import java.util.Map;

public abstract class BaseHandler {
    /**
     * 类型
     */
    protected String type;

    /**
     * 统一下单
     *
     * @param config
     * @param data
     * @return
     */
    public abstract ReturnVO unifiedOrder(WxPayConfig config, Map<String, String> data);

}
