package top.zsmile.pay.service.impl;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.wechat.pay.java.service.refund.model.Refund;
import io.micrometer.core.instrument.util.TimeUtils;
import org.redisson.api.RLock;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.core.utils.Asserts;
import top.zsmile.common.redis.utils.RedisLock;
import top.zsmile.pay.constant.*;
import top.zsmile.pay.domain.SysTransaction;
import top.zsmile.pay.domain.SysTransactionRefund;
import top.zsmile.pay.handler.HandlerFactory;
import top.zsmile.pay.service.*;
import top.zsmile.pay.utils.OrderUtils;
import top.zsmile.pay.vo.MiniPrepayVO;
import top.zsmile.pay.vo.NaivePrepayVO;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 订单服务
 */
@Service
public class TransactionServiceImpl implements ITransactionService {

    @Resource
    private ISysTransactionService sysTransactionService;

    @Resource
    private IWechatPayService wechatPayService;

    @Resource
    private IAliPayService aliPayService;

    @Resource
    private RedisLock redisLock;

    @Override
    public SysTransaction create(String id, String handleType, String payType, Integer expireIn, String sceneInfo, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate) {
        return create(id, handleType, payType, expireIn, sceneInfo, "CNY", tradeType, quantity, price, rate);
    }

    @Override
    public SysTransaction create(String id, String handleType, String payType, Integer expireIn, String sceneInfo, String payCurrency, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate) {
        if (!TradeConstant.PayType.support(payType)) {
            throw new IllegalArgumentException("暂不支持该支付方式=>" + payType);
        }
        if (handleType != null && !HandlerFactory.support(handleType)) {
            throw new IllegalArgumentException("暂不支持该处理方式=>" + handleType);
        }

        SysTransaction transaction = new SysTransaction();
        transaction.setHandleType(handleType);
        transaction.setOrderNo(OrderUtils.getOrderId());
        transaction.setSceneInfo(sceneInfo);
        transaction.setTradeState(TradeConstant.TradeState.NOTPAY);
        transaction.setPayCurrency(payCurrency);
        transaction.setPayType(payType);
        transaction.setTradeType(tradeType);
        transaction.setQuantity(quantity);
        transaction.setPrice(price.multiply(TradeRateConstant.HUNDRED));
        BigDecimal amount = transaction.getPrice().multiply(BigDecimal.valueOf(quantity));
        transaction.setAmount(amount);
        // 没有优惠卷等减额
        transaction.setRecePrice(amount);
        // 扣除费率
        BigDecimal realPrice = amount.multiply(rate).setScale(0, RoundingMode.HALF_UP);
        transaction.setRate(rate);
        transaction.setRealPrice(realPrice);

        LocalDateTime now = LocalDateTime.now();
        transaction.setCreateTime(now);
        transaction.setExpireIn(expireIn);
        transaction.setExpireTime(now.plusMinutes(expireIn));
        return transaction;
    }

    @Override
    @Transactional
    public SysTransaction createAndSave(String id, String handleType, String payType, Integer expireIn, String sceneInfo, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate) {
        return createAndSave(id, handleType, payType, expireIn, sceneInfo, "CNY", tradeType, quantity, price, rate);
    }

    @Override
    @Transactional
    public SysTransaction createAndSave(String id, String handleType, String payType, Integer expireIn, String sceneInfo, String payCurrency, String tradeType, Integer quantity, BigDecimal price, BigDecimal rate) {
        SysTransaction transaction = create(id, handleType, payType, expireIn, sceneInfo, payCurrency, tradeType, quantity, price, rate);
        sysTransactionService.insertSysTransaction(transaction);
        return transaction;
    }

    @Override
    public void save(SysTransaction sysTransaction) {
        sysTransactionService.insertSysTransaction(sysTransaction);
    }

    @Override
    @Transactional
    public NaivePrepayVO scanPrepay(String id, SysTransaction transaction) {
        NaivePrepayVO naivePrepayVO = null;
        if (TradeConstant.PayType.WXPAY.equals(transaction.getPayType())) {
            PrepayResponse prepayResponse = wechatPayService.naivePay(id, transaction);
            naivePrepayVO = NaivePrepayVO.of(transaction.getId(), prepayResponse.getCodeUrl(), transaction.getExpireTime());
        } else if (TradeConstant.PayType.ALIPAY.equals(transaction.getPayType())) {
            String qrcode = aliPayService.scanPrePay(id, transaction);
            naivePrepayVO = NaivePrepayVO.of(transaction.getId(), qrcode, transaction.getExpireTime());
        } else {
            // TODO 需要去掉，用于审核
            transaction.setPayType(TradeConstant.PayType.ALIPAY);
            String pageUrl = aliPayService.pcWebPay(id, transaction);
            naivePrepayVO = NaivePrepayVO.of(transaction.getId(), pageUrl, transaction.getExpireTime());
        }
        return naivePrepayVO;
    }

    @Override
    @Transactional
    public MiniPrepayVO miniPrepay(String id, SysTransaction transaction) {
        MiniPrepayVO miniPrepayVO = null;
        if (TradeConstant.PayType.WXPAY.equals(transaction.getPayType())) {
            miniPrepayVO = wechatPayService.miniPayPack(id, transaction);
        } else {
            throw new SXException("暂不支持");
        }
        return miniPrepayVO;
    }

    @Override
    public void refund(long transactionId, BigDecimal refundMoney) {
        SysTransaction transaction = sysTransactionService.getById(transactionId);
        Asserts.isNull(transaction, "订单不存在");
        refund(transaction, refundMoney);
    }

    @Override
    @Transactional
    public void refund(SysTransaction transaction, BigDecimal refundMoney) {
        BigDecimal refundMoneyRate = refundMoney.divide(TradeRateConstant.HUNDRED);
        if (transaction.getRefundPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new SXException("订单已完全退款");
        }
        BigDecimal canRefund = transaction.getRecePrice().subtract(transaction.getRefundPrice());
        if (canRefund.compareTo(refundMoneyRate) < 0) {
            throw new SXException("订单余额不足");
        }
        if (redisLock.tryLock(TradeCacheConstant.REFUND_STATUS + transaction.getId(), 5, 10, TimeUnit.SECONDS)) {
            canRefund = transaction.getRecePrice().subtract(transaction.getRefundPrice());
            if (canRefund.compareTo(refundMoneyRate) < 0) {
                throw new SXException("订单余额不足");
            }
            SysTransactionRefund sysTransactionRefund = new SysTransactionRefund();
            sysTransactionRefund.setRefundNo(OrderUtils.getOrderId());
            sysTransactionRefund.setCreateTime(LocalDateTime.now());
            sysTransactionRefund.setRefundStatus(RefundEnums.PROCESSING.name());
            sysTransactionRefund.setChannel(TradeRefundChannelConstant.ORIGINAL);
            sysTransactionRefund.setQuantity(1);
            String appid = transaction.getAppid();
            if (appid == null) {
                throw new SXException("应用ID不存在");
            }
            if (TradeConstant.PayType.WXPAY.equals(transaction.getPayType())) {
                Refund refund = wechatPayService.refund(appid, transaction, sysTransactionRefund);
                // TODO refund something
            } else if (TradeConstant.PayType.ALIPAY.equals(transaction.getPayType())) {
                AlipayTradeRefundResponse response = aliPayService.refund(appid, transaction, sysTransactionRefund);
                sysTransactionRefund.setOutRefundNo(response.getTradeNo());
                sysTransactionRefund.setUserReceivedAccount(response.getRefundDetailItemList().get(0).getFundChannel());
                sysTransactionRefund.setRefundStatus(RefundEnums.SUCCESS.name());
                sysTransactionRefund.setSuccessTime(LocalDateTime.now());
                if (transaction.getTradeState().equals(TradeConstant.TradeState.REFUND)) {
                    transaction.setUpdateTime(LocalDateTime.now());
                    transaction.setRefundPrice(new BigDecimal(response.getRefundFee()).multiply(new BigDecimal(100)));
                    if (transaction.getRecePrice().compareTo(transaction.getRefundPrice()) > 0) {
                        transaction.setTradeState(TradeConstant.TradeState.SECTION_REFUND);
                    } else {
                        transaction.setTradeState(TradeConstant.TradeState.FINISH);
                    }
//                    transactionService.lambdaUpdate()
//                            .set(SysTransaction::getTradeState, transaction.getTradeState())
//                            .set(SysTransaction::getUpdateTime, transaction.getUpdateTime())
//                            .set(SysTransaction::getRefundPrice, transaction.getRefundPrice())
//                            .eq(SysTransaction::getId, transaction.getId()).update();
                }

            }
        }
    }
}

