package top.zsmile.pay.handler;

import top.zsmile.pay.domain.SysTransaction;
import com.wechat.pay.java.service.payments.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHandler {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 统一下单
     *
     * @param transaction    订单信息
     * @param sysTransaction
     * @return
     */
    public abstract void exec(Transaction transaction, SysTransaction sysTransaction);
}
