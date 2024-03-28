package top.zsmile.tool.pay.entity.dto;

import java.io.Serializable;

/**
 * 查询订单
 */
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 微信订单号。建议优先使用
     */
    private String transactionId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
