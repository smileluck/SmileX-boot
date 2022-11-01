package top.zsmile.pay.wechat.v2.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.zsmile.core.exception.SXException;
import top.zsmile.pay.entity.vo.AppPayVO;
import top.zsmile.pay.entity.vo.ReturnVO;
import top.zsmile.pay.enums.PayTradeTypeEnum;
import top.zsmile.pay.wechat.v2.WxPayCore;
import top.zsmile.pay.wechat.v2.WxPayUtil;
import top.zsmile.pay.wechat.v2.config.WxPayConfig;
import top.zsmile.pay.factory.PayTradeTypeFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AppHandler extends BaseHandler implements InitializingBean {

    @Override
    public ReturnVO unifiedOrder(WxPayConfig config, Map<String, String> data) {
        WxPayCore wxPayCore = WxPayCore.of(config);
        try {
            Map<String, String> resMap = wxPayCore.unifiedOrder(data);
            ReturnVO returnVO = WxPayUtil.mapToResult(config,resMap);
            log.debug("{} unifiedOrder result ==> {}", this.type, returnVO);
            if (WxPayUtil.checkResponseState(returnVO)) {
                AppPayVO appPayVO = packageAppResult(config, returnVO);
                returnVO.setAppPayVO(appPayVO);
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
        this.type = PayTradeTypeEnum.APP.getValue();
        PayTradeTypeFactory.register(this.type, this);
    }


    /**
     * 包装并签名返回给APP调用的实体
     *
     * @return
     */
    public static AppPayVO packageAppResult(WxPayConfig config, ReturnVO returnVO) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (!config.isMch()) {
            map.put("appid", returnVO.getAppId());
        }
        map.put("partnerid", returnVO.getMchId());
        map.put("prepayid", returnVO.getPrepayId());
        map.put("package", "Sign=WXPay");
        map.put("timestamp", LocalDateTime.now().getNano() / 1000 + "");
        map.put("noncestr", returnVO.getNonceStr());
        String signature = WxPayUtil.generateSignature(map, config.getKey());
        map.put("sign", signature);
        return AppPayVO.of(map);
    }
}
