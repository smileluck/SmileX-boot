package top.zsmile.tool.pay.wechat.v2.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.tool.pay.entity.vo.JsApiPayVO;
import top.zsmile.tool.pay.entity.vo.ReturnVO;
import top.zsmile.tool.pay.enums.PayTradeTypeEnum;
import top.zsmile.tool.pay.factory.PayTradeTypeFactory;
import top.zsmile.tool.pay.wechat.v2.WxPayCore;
import top.zsmile.tool.pay.wechat.v2.WxPayUtil;
import top.zsmile.tool.pay.wechat.v2.config.WxPayConfig;

import java.util.Map;

/**
 * JSAPI 下单类
 */
@Slf4j
@Component
public class JsapiHandler extends BaseHandler implements InitializingBean {

    @Override
    public ReturnVO unifiedOrder(WxPayConfig config, Map<String, String> data) {
        WxPayCore wxPayCore = WxPayCore.of(config);
        try {
            Map<String, String> resMap = wxPayCore.unifiedOrder(data);
            ReturnVO returnVO = WxPayUtil.mapToResult(config,resMap);
            log.debug("{} unifiedOrder result ==> {}",this.type, returnVO);
            if (WxPayUtil.checkResponseState(returnVO)) {
                // 包装调起类信息
                JsApiPayVO jsApiPayVO = WxPayUtil.packageJsApiResult(config, returnVO);
                returnVO.setJsApiPayVO(jsApiPayVO);
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
        this.type = PayTradeTypeEnum.JSAPI.getValue();
        PayTradeTypeFactory.register(this.type, this);
    }

}
