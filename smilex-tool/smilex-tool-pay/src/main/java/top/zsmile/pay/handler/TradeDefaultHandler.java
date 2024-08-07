package top.zsmile.pay.handler;

import com.wechat.pay.java.service.refund.model.RefundNotification;
import jodd.util.CollectionUtil;
import org.redisson.api.RLock;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.core.utils.LocalDateUtils;
import top.zsmile.common.redis.utils.RedisLock;
import top.zsmile.pay.constant.RefundEnums;
import top.zsmile.pay.constant.TradeCacheConstant;
import top.zsmile.pay.constant.TradeConstant;
import top.zsmile.pay.domain.SysTransaction;
import top.zsmile.pay.domain.SysTransactionRefund;
import top.zsmile.pay.service.ISysTransactionRefundService;
import top.zsmile.pay.service.ISysTransactionService;
import top.zsmile.pay.service.IWechatStorageService;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.model.TransactionPayer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 主要处理
 */
@Service
public class TradeDefaultHandler implements InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(TradeDefaultHandler.class);

    @Resource
    private ISysTransactionService sysTransactionService;

    @Resource
    private ISysTransactionRefundService sysTransactionRefundService;

    @Resource
    private IWechatStorageService wechatStorageService;

    @Resource
    private RedisLock redisLock;

    /**
     * 微信执行
     *
     * @param transaction 微信通知结果
     */
    @Transactional
    public void wxExec(Transaction transaction) {

        SysTransaction sysTransaction = sysTransactionService.selectSysTransactionByOrderNo(transaction.getOutTradeNo());
        if (sysTransaction == null) {
            throw new SXException("订单不存在");
        }
        if (sysTransaction.getTradeState().equals(TradeConstant.TradeState.NOTPAY)) {
            TransactionPayer payer = transaction.getPayer();
            sysTransaction.setOutOrderNo(transaction.getTransactionId());
            sysTransaction.setTradeState(transaction.getTradeState().name());
            sysTransaction.setBankType(transaction.getBankType());

            LocalDateTime payTime = null;
            payTime = LocalDateUtils.parse(transaction.getSuccessTime(), LocalDateUtils.FORMAT_RFC3339);
            sysTransaction.setPayTime(payTime);
            sysTransaction.setOpenid(payer.getOpenid());
            sysTransaction.setSuccessTime(LocalDateTime.now());
//            sysTransactionService.lambdaUpdate()
//                    .set(SysTransaction::getOutOrderNo, sysTransaction.getOutOrderNo())
//                    .set(SysTransaction::getTradeState, sysTransaction.getTradeState())
//                    .set(SysTransaction::getBankType, sysTransaction.getBankType())
//                    .set(SysTransaction::getPayTime, payTime)
//                    .set(SysTransaction::getOpenid, sysTransaction.getOpenid())
//                    .set(SysTransaction::getSuccessTime, sysTransaction.getSuccessTime())
//                    .eq(SysTransaction::getId, sysTransaction.getId()).update();

            wechatStorageService.saveTransactionStatus(sysTransaction.getId().toString(), sysTransaction.getTradeState());

            if (StringUtils.isNotBlank(sysTransaction.getHandleType())) {
                AbstractHandler abstractHandler = HandlerFactory.get(sysTransaction.getHandleType());
                if (abstractHandler != null) {
                    abstractHandler.execWx(transaction, sysTransaction);
                }
            }
        }
    }

    @Transactional
    public void wxExecRefund(RefundNotification notification) {
        Map<String, String> outTradeNo = Collections.singletonMap("out_trade_no", notification.getOutTradeNo());
        SysTransactionRefund sysTransactionRefund = sysTransactionRefundService.getObjByMap(outTradeNo);
        if (sysTransactionRefund == null) {
            throw new SXException("订单不存在");
        }
        SysTransaction sysTransaction = sysTransactionService.selectSysTransactionById(sysTransactionRefund.getTransactionId());


        RLock lock = redisLock.getRLock(TradeCacheConstant.REFUND_STATUS + sysTransaction.getId());

        try {
            if (lock != null && lock.tryLock(10, 30, TimeUnit.SECONDS)) {
                if (sysTransactionRefund.getRefundStatus().equals(RefundEnums.PROCESSING.name())) {

                    sysTransactionRefund.setOutRefundNo(notification.getRefundId());
                    sysTransactionRefund.setRefundStatus(notification.getRefundStatus().name());
                    LocalDateTime refundTime = LocalDateUtils.parse(notification.getSuccessTime(), LocalDateUtils.FORMAT_RFC3339);
                    sysTransactionRefund.setSuccessTime(refundTime);
                    sysTransactionRefund.setUserReceivedAccount(notification.getUserReceivedAccount());
                    sysTransactionRefund.setPrice(new BigDecimal(notification.getAmount().getRefund()));
//                    sysTransactionRefundService.lambdaUpdate()
//                            .set(SysTransactionRefund::getOutRefundNo, sysTransactionRefund.getOutRefundNo())
//                            .set(SysTransactionRefund::getRefundStatus, sysTransactionRefund.getRefundStatus())
//                            .set(SysTransactionRefund::getSuccessTime, sysTransactionRefund.getSuccessTime())
//                            .set(SysTransactionRefund::getUserReceivedAccount, sysTransactionRefund.getUserReceivedAccount())
//                            .set(SysTransactionRefund::getPrice, sysTransactionRefund.getPrice())
//                            .eq(SysTransactionRefund::getId, sysTransactionRefund.getId()).update();

                    sysTransactionRefundService.updateById(sysTransactionRefund);

                    BigDecimal bigDecimal = sysTransaction.getRefundPrice().add(sysTransactionRefund.getPrice());
                    sysTransaction.setRefundPrice(bigDecimal);
                    if (sysTransaction.getRecePrice().compareTo(sysTransaction.getRefundPrice()) > 0) {
                        sysTransaction.setTradeState(TradeConstant.TradeState.SECTION_REFUND);
                    } else {
                        sysTransaction.setTradeState(TradeConstant.TradeState.FINISH);
                    }

                    sysTransactionService.updateSysTransaction(sysTransaction);


                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }

    }


    /**
     * 支付宝执行
     *
     * @param params 支付宝通知结果
     */
    @Transactional
    public void aliExec(Map<String, String> params) {

        SysTransaction sysTransaction = sysTransactionService.selectSysTransactionByOrderNo(params.get("out_trade_no"));
        if (sysTransaction == null) {
            throw new SXException("订单不存在");
        }
        if (sysTransaction.getTradeState().equals(TradeConstant.TradeState.NOTPAY)) {
            sysTransaction.setOutOrderNo(params.get("trade_no"));
            sysTransaction.setTradeState(TradeConstant.TradeState.convert(TradeConstant.PayType.ALIPAY, params.get("trade_status")));
//            sysTransaction.setBankType(transaction.getBankType());

            LocalDateTime payTime = null;
            payTime = LocalDateUtils.parse(params.get("gmt_payment"), LocalDateUtils.FORMAT_DEFAULT);
            sysTransaction.setRealPrice(new BigDecimal(params.get("receipt_amount")));
            sysTransaction.setPayTime(payTime);
            sysTransaction.setOpenid(params.get("buyer_id"));

            wechatStorageService.saveTransactionStatus(sysTransaction.getId().toString(), sysTransaction.getTradeState());
            if (StringUtils.isNotBlank(sysTransaction.getHandleType())) {
                AbstractHandler abstractHandler = HandlerFactory.get(sysTransaction.getHandleType());
                if (abstractHandler != null) {
                    abstractHandler.execAli(params, sysTransaction);
                }
            }
            sysTransaction.setSuccessTime(LocalDateTime.now());
//            sysTransactionService.lambdaUpdate()
//                    .set(SysTransaction::getOutOrderNo, sysTransaction.getOutOrderNo())
//                    .set(SysTransaction::getTradeState, sysTransaction.getTradeState())
//                    .set(SysTransaction::getBankType, sysTransaction.getBankType())
//                    .set(SysTransaction::getRealPrice, sysTransaction.getRealPrice())
//                    .set(SysTransaction::getPayTime, payTime)
//                    .set(SysTransaction::getOpenid, sysTransaction.getOpenid())
//                    .set(SysTransaction::getSuccessTime, sysTransaction.getSuccessTime())
//                    .eq(SysTransaction::getId, sysTransaction.getId()).update();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HandlerFactory.init(this);
    }
}
