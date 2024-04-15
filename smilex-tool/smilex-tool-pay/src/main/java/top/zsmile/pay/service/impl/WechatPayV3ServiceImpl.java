package top.zsmile.pay.service.impl;

import top.zsmile.common.core.utils.LocalDateUtils;
import top.zsmile.common.core.utils.http.HttpHelper;
import top.zsmile.pay.bean.WxV3Resp;
import top.zsmile.pay.bean.WxV3Storage;
import top.zsmile.pay.domain.SysTransaction;
import top.zsmile.pay.handler.HandlerFactory;
import top.zsmile.pay.properties.WechatPayV3Properties;
import top.zsmile.pay.service.IWechatPayService;
import top.zsmile.pay.service.IWechatStorageService;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 微信支付V3接口
 */
@Service
public class WechatPayV3ServiceImpl implements IWechatPayService {

    private static final Logger log = LoggerFactory.getLogger(WechatPayV3ServiceImpl.class);

    @Resource
    private IWechatStorageService wechatStorageService;

    @Override
    public PrepayResponse naivePay(SysTransaction transaction) {
        PrepayRequest prepayRequest = new PrepayRequest();
        prepayRequest.setAppid(transaction.getAppid());
        prepayRequest.setMchid(transaction.getMchid());
        prepayRequest.setOutTradeNo(transaction.getOrderNo());
        prepayRequest.setDescription(transaction.getSceneInfo());
        prepayRequest.setNotifyUrl(transaction.getNotifyUrl());
        Amount amount = new Amount();
        amount.setTotal(transaction.getAmount().intValue());
        prepayRequest.setAmount(amount);
        if (transaction.getExpireTime() != null) {
            prepayRequest.setTimeExpire(LocalDateUtils.format(transaction.getExpireTime(), LocalDateUtils.FORMAT_RFC3339));
        }
        NativePayService service = new NativePayService.Builder().config(transaction.getConfig()).build();
        PrepayResponse prepay = service.prepay(prepayRequest);
        wechatStorageService.saveTransactionStatus(transaction.getId().toString(), transaction.getTradeState());
        return prepay;
    }

    @Override
    public Transaction validAndRepeat(String id, HttpServletRequest request) {
        WechatPayV3Properties wechatPayV3Properties = wechatStorageService.get(id);
        String serial = request.getHeader("Wechatpay-Serial");
        if (StringUtils.isBlank(serial)) {
            log.debug("Wechatpay-Serial is blank");
            return null;
        }
        if (!wechatPayV3Properties.getMchSerialNum().equals(serial)) {
            log.debug("Wechatpay-Serial is correct");
            return null;
        }
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        if (StringUtils.isBlank(timestamp)) {
            log.debug("Wechatpay-Timestamp is blank");
            return null;
        }
        String nonce = request.getHeader("Wechatpay-Nonce");
        if (StringUtils.isBlank(nonce)) {
            log.debug("Wechatpay-Nonce is blank");
            return null;
        }
        String signature = request.getHeader("Wechatpay-Signature");
        if (StringUtils.isBlank(signature)) {
            log.debug("Wechatpay-Nonce is blank");
            return null;
        }


        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(serial)
                .nonce(nonce)
                .signature(signature)
                .timestamp(timestamp)
                .body(HttpHelper.getBodyString(request))
                .build();
        try {
            WxV3Storage storage = wechatStorageService.getConfig(id);

// 初始化 NotificationParser
            NotificationParser parser = new NotificationParser(storage.getConfig());
            // 以支付通知回调为例，验签、解密并转换成 Transaction
            Transaction transaction = parser.parse(requestParam, Transaction.class);
            return transaction;
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            log.error("sign verification failed", e);
            return null;
        }
    }

    @Override
    public WxV3Resp notifyHandle(String id, HttpServletRequest request) {
        Transaction transaction = validAndRepeat(id, request);
        if (transaction == null) {
            return WxV3Resp.fail("sign verification failed");
        }

        if (!wechatStorageService.repeatNotify(transaction.getTransactionId())) {
            return WxV3Resp.fail("repeat handle");
        }

        HandlerFactory.exec(transaction);
        return WxV3Resp.success();
    }

}
