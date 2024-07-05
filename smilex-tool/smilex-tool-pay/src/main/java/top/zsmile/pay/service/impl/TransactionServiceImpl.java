package top.zsmile.pay.service.impl;

import top.zsmile.common.core.exception.SXException;
import top.zsmile.pay.constant.TradeConstant;
import top.zsmile.pay.constant.TradeRateConstant;
import top.zsmile.pay.domain.SysTransaction;
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
}
