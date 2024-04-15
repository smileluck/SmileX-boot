package top.zsmile.pay.handler;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.cglib.core.Local;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.core.utils.LocalDateUtils;
import top.zsmile.common.mybatis.meta.conditions.udpate.UpdateWrapper;
import top.zsmile.pay.constant.TradeConstant;
import top.zsmile.pay.domain.SysTransaction;
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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 主要处理
 */
@Service
public class TradeDefaultHandler implements InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(TradeDefaultHandler.class);

    @Resource
    private ISysTransactionService sysTransactionService;

    @Resource
    private IWechatStorageService wechatStorageService;

    @Transactional
    public void exec(Transaction transaction) {

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
            //TODO 转 lambda 完善
            sysTransactionService.update(new UpdateWrapper<>()
//                            .set(SysTransaction::getOutOrderNo, sysTransaction.getOutOrderNo())
//                            .set(SysTransaction::getTradeState, sysTransaction.getTradeState())
//                            .set(SysTransaction::getBankType, sysTransaction.getBankType())
//                            .set(SysTransaction::getPayTime, payTime)
//                            .set(SysTransaction::getOpenid, sysTransaction.getOpenid())
//                            .set(SysTransaction::getSuccessTime, sysTransaction.getSuccessTime())
//                            .eq(SysTransaction::getId, sysTransaction.getId())
                    );

            wechatStorageService.saveTransactionStatus(sysTransaction.getId().toString(), sysTransaction.getTradeState());

            if (StringUtils.isNotBlank(sysTransaction.getHandleType())) {
                AbstractHandler abstractHandler = HandlerFactory.get(sysTransaction.getHandleType());
                if (abstractHandler != null) {
                    abstractHandler.exec(transaction, sysTransaction);
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HandlerFactory.init(this);
    }
}
