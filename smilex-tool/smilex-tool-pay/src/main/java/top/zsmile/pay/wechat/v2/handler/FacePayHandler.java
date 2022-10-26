package top.zsmile.pay.wechat.v2.handler;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.zsmile.pay.enums.PayTradeTypeEnum;
import top.zsmile.pay.wechat.v2.config.WxPayConfig;
import top.zsmile.pay.wechat.v2.factory.PayTradeTypeFactory;

import java.util.Map;

@Component
public class FacePayHandler extends BaseHandler implements InitializingBean {

    @Override
    public Map unifiedOrder(WxPayConfig config) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.type = PayTradeTypeEnum.FACE_PAY.getValue();
        PayTradeTypeFactory.register(PayTradeTypeEnum.FACE_PAY.getValue(), this);
    }
}
