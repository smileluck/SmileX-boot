package top.zsmile.pay.service.impl;

import top.zsmile.pay.bean.WxV3Storage;
import top.zsmile.pay.constant.TradeConstant;
import top.zsmile.pay.constant.TradeRateConstant;
import top.zsmile.pay.domain.SysTransaction;
import top.zsmile.pay.handler.HandlerFactory;
import top.zsmile.pay.service.ISysTransactionService;
import top.zsmile.pay.service.ITransactionService;
import top.zsmile.pay.service.IWechatStorageService;
import top.zsmile.pay.utils.OrderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 订单服务
 */
@Service
public class TransactionServiceImpl implements ITransactionService {

    @Resource
    private IWechatStorageService wechatStorageService;

    @Resource
    private ISysTransactionService sysTransactionService;

    @Override
    @Transactional
    public SysTransaction create(String id, String handleType, String payType, Integer expireIn, String sceneInfo, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate) {
        return create(id, handleType, payType, expireIn, sceneInfo, "CNY", tradeType, quantity, price, rate);
    }

    @Override
    @Transactional
    public SysTransaction create(String id, String handleType, String payType, Integer expireIn, String sceneInfo, String payCurrency, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate) {
        if (!TradeConstant.PayType.support(payType)) {
            throw new IllegalArgumentException("暂不支持该支付方式=>" + payType);
        }
        if (handleType != null && !HandlerFactory.support(handleType)) {
            throw new IllegalArgumentException("暂不支持该处理方式=>" + handleType);
        }

        WxV3Storage storage = wechatStorageService.getConfig(id);
        SysTransaction transaction = new SysTransaction();
        transaction.setAppid(storage.getAppid());
        transaction.setMchid(storage.getMchid());
        transaction.setHandleType(handleType);
        transaction.setOrderNo(OrderUtils.getOrderId());
        transaction.setSceneInfo(sceneInfo);
        transaction.setTradeState(TradeConstant.TradeState.NOTPAY);
        transaction.setPayCurrency(payCurrency);
        transaction.setPayType(payType);
        transaction.setTradeType(tradeType);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity)).multiply(TradeRateConstant.HUNDRED);
        transaction.setAmount(amount);
        // 没有优惠卷等减额
        transaction.setRecePrice(amount);
        // 扣除费率
        BigDecimal realPrice = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        transaction.setRate(rate);
        transaction.setRealPrice(realPrice);
        transaction.setNotifyUrl(storage.getNotifyUrl());
        transaction.setConfig(storage.getConfig());

        LocalDateTime now = LocalDateTime.now();
        transaction.setCreateTime(now);
        transaction.setExpireTime(now.plusMinutes(expireIn));
        sysTransactionService.insertSysTransaction(transaction);

        return transaction;
    }
}
