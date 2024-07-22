package top.zsmile.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.core.utils.LocalDateUtils;
import top.zsmile.pay.bean.AliStorage;
import top.zsmile.pay.bean.WxV3Resp;
import top.zsmile.pay.constant.TradeRateConstant;
import top.zsmile.pay.domain.SysTransaction;
import top.zsmile.pay.domain.SysTransactionRefund;
import top.zsmile.pay.handler.HandlerFactory;
import top.zsmile.pay.properties.AliPayConfigProperties;
import top.zsmile.pay.service.IAliPayService;
import top.zsmile.pay.service.IAliStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class AliPayServiceImpl implements IAliPayService {

    private static final Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);

    @Resource
    private IAliStorageService aliStorageService;


    @Override
    public String scanPrePay(String id, SysTransaction transaction) {
        AliStorage storage = aliStorageService.get(id);
        AliPayConfigProperties properties = storage.getProperties();
        transaction.setAppid(properties.getAppid());
        AlipayClient alipayClient = storage.getClient();

        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(properties.getNotifyUrl());
//        JSONObject model = new JSONObject();
//        model.put("out_trade_no", transaction.getOrderNo());
//        model.put("total_amount", transaction.getAmount().divide(TradeRateConstant.HUNDRED).doubleValue());
//        model.put("subject", transaction.getSceneInfo());
//        model.put("qr_code_timeout_express", transaction.getExpireIn());
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(transaction.getOrderNo());
        model.setTotalAmount(transaction.getAmount().divide(TradeRateConstant.HUNDRED).toString());
        model.setSubject(transaction.getSceneInfo());
        model.setTimeExpire(LocalDateUtils.format(transaction.getExpireTime(), LocalDateUtils.FORMAT_DEFAULT));
//// 商品明细信息，按需传入
//JSONArray goodsDetail = new JSONArray();
//JSONObject goods1 = new JSONObject();
//goods1.put("goods_id", "goodsNo1");
//goods1.put("goods_name", "子商品1");
//goods1.put("quantity", 1);
//goods1.put("price", 0.01);
//goodsDetail.add(goods1);
//model.put("goods_detail", goodsDetail);

//// 扩展信息，按需传入
//JSONObject extendParams = new JSONObject();
//extendParams.put("sys_service_provider_id", "2088511833207846");
//model.put("extend_params", extendParams);

//// 结算信息，按需传入
//JSONObject settleInfo = new JSONObject();
//JSONArray settleDetailInfos = new JSONArray();
//JSONObject settleDetail = new JSONObject();
//settleDetail.put("trans_in_type", "defaultSettle");
//settleDetail.put("amount", 0.01);
//settleDetailInfos.add(settleDetail);
//settleInfo.put("settle_detail_infos", settleDetailInfos);
//model.put("settle_info", settleInfo);

//// 二级商户信息，按需传入
//JSONObject subMerchant = new JSONObject();
//subMerchant.put("merchant_id", "2088000603999128");
//model.put("sub_merchant", subMerchant);

//// 业务参数信息，按需传入
//JSONObject businessParams = new JSONObject();
//businessParams.put("busi_params_key", "busiParamsValue");
//model.put("business_params", businessParams);

//// 营销信息，按需传入
//JSONObject promoParams = new JSONObject();
//promoParams.put("promo_params_key", "promoParamsValue");
//model.put("promo_params", promoParams);

        request.setBizModel(model);
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return response.getQrCode();
            } else {
                logger.error("AlipayTradePrecreateResponse fail => {}", response.getMsg());
                throw new SXException("二维码获取失败");
            }
        } catch (AlipayApiException e) {
            logger.error("AlipayTradePrecreateResponse error => {}", e.getMessage());
            throw new SXException("二维码获取失败");
        }
    }

    @Override
    public String pcWebPay(String id, SysTransaction transaction) {
        AliStorage storage = aliStorageService.get(id);
        AliPayConfigProperties properties = storage.getProperties();
        transaction.setAppid(properties.getAppid());
        AlipayClient alipayClient = storage.getClient();

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
//异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(properties.getNotifyUrl());
//同步跳转地址，仅支持http/https
        request.setReturnUrl("");
/******必传参数******/
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();

        model.setOutTradeNo(transaction.getOrderNo());
        model.setTotalAmount(transaction.getAmount().divide(TradeRateConstant.HUNDRED).toString());
        model.setSubject(transaction.getSceneInfo());
        model.setTimeExpire(LocalDateUtils.format(transaction.getExpireTime(), LocalDateUtils.FORMAT_DEFAULT));
//电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        model.setQrPayMode("2");

/******可选参数******/
//model.put("time_expire", "2022-08-01 22:00:00");

//// 商品明细信息，按需传入
//JSONArray goodsDetail = new JSONArray();
//JSONObject goods1 = new JSONObject();
//goods1.put("goods_id", "goodsNo1");
//goods1.put("goods_name", "子商品1");
//goods1.put("quantity", 1);
//goods1.put("price", 0.01);
//goodsDetail.add(goods1);
//model.put("goods_detail", goodsDetail);

//// 扩展信息，按需传入
//JSONObject extendParams = new JSONObject();
//extendParams.put("sys_service_provider_id", "2088511833207846");
//model.put("extend_params", extendParams);

        request.setBizModel(model);
        AlipayTradePagePayResponse response;
        try {
            response = alipayClient.pageExecute(request, "POST");
            if (response.isSuccess()) {
                return response.getBody();
            } else {
                logger.error("AlipayTradePrecreateResponse fail => {}", response.getMsg());
                throw new SXException("二维码获取失败");
            }
        } catch (AlipayApiException e) {
            logger.error("AlipayTradePrecreateResponse error => {}", e.getMessage());
            throw new SXException("二维码获取失败");
        }

    }

    @Override
    public String notifyHandle(String id, HttpServletRequest request) {
//获取支付宝POST过来反馈信息，将异步通知中收到的待验证所有参数都存放到map中
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //调用SDK验证签名
        //公钥验签示例代码
        try {
            AliStorage storage = aliStorageService.get(id);
            AliPayConfigProperties properties = storage.getProperties();
            boolean signVerified = AlipaySignature.rsaCheckV1(params, properties.getAlipayPublicKey(), properties.getCharset(), properties.getSignType());
            if (signVerified) {
                String notifyId = params.get("notify_id");
                if (aliStorageService.repeatNotify(notifyId)) {
                    //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success

                    HandlerFactory.exec(params);
                    return WxV3Resp.RES_SUCCESS;
                } else {
                    logger.error("repeat notify => {}", notifyId);
                    return "notify repeat";
                }
            } else {
                logger.error("notify signature verified fail");
                return "notify signature fail";
            }
        } catch (AlipayApiException e) {
            logger.error("notify signature verified error => {}", e.getMessage());
            return "notify signature error";
        }
        //公钥证书验签示例代码
        //   boolean flag = AlipaySignature.rsaCertCheckV1(params,alipayPublicCertPath,"UTF-8","RSA2");
    }

    @Override
    public AlipayTradeRefundResponse refund(String appid, SysTransaction transaction, SysTransactionRefund transactionRefund) {

        AliStorage storage = aliStorageService.get(appid);
        AliPayConfigProperties properties = storage.getProperties();
        AlipayClient alipayClient = storage.getClient();
        // 构造请求参数以调用接口
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setNotifyUrl(properties.getNotifyRefundUrl());
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        // 设置商户订单号
        model.setTradeNo(transaction.getOutOrderNo());
        model.setOutRequestNo(transactionRefund.getRefundNo());
        // 设置退分账明细信息
        // 设置退款金额
        model.setRefundAmount(String.valueOf(transactionRefund.getPrice().divide(new BigDecimal(100))));

        model.setQueryOptions(new ArrayList<>(Arrays.asList("refund_detail_item_list")));
        // 设置退款原因说明
//        model.setRefundReason("正常退款");
        request.setBizModel(model);
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return response;
            } else {
                throw new RuntimeException("调用失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
