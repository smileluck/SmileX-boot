package top.zsmile.pay.wechat.v2.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.zsmile.core.exception.SXException;
import top.zsmile.pay.entity.vo.ReturnVO;
import top.zsmile.pay.enums.PayTradeTypeEnum;
import top.zsmile.pay.wechat.v2.WxPayCore;
import top.zsmile.pay.wechat.v2.WxPayUtil;
import top.zsmile.pay.wechat.v2.config.WxPayConfig;
import top.zsmile.pay.factory.PayTradeTypeFactory;

import java.util.Map;

/**
 * H5支付
 */
@Slf4j
@Component
public class H5PayHandler extends BaseHandler implements InitializingBean {

    @Override
    public ReturnVO unifiedOrder(WxPayConfig config, Map<String, String> data) {
        WxPayCore wxPayCore = WxPayCore.of(config);
        try {
            Map<String, String> resMap = wxPayCore.unifiedOrder(data);
            ReturnVO returnVO = WxPayUtil.mapToResult(resMap);
            log.debug("{} unifiedOrder result ==> {}",this.type, returnVO);
            if (WxPayUtil.checkResultState(returnVO)) {
                return returnVO;
            } else {
                throw new SXException("下单异常，异常原因：" + returnVO.getReturnMsg());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new SXException("下单异常，请联系管理员");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.type = PayTradeTypeEnum.H5_PAY.getValue();
        PayTradeTypeFactory.register(this.type, this);
    }
}
