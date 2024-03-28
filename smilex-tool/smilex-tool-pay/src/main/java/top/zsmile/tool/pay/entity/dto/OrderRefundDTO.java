package top.zsmile.tool.pay.entity.dto;


import top.zsmile.tool.pay.enums.RefundAccountEnum;

import java.io.Serializable;

/**
 * 订单退款
 */
public class OrderRefundDTO extends OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户退款单号
     */
    private String outRefundNo;

    /**
     * 订单金额
     */
    private Integer totalFee;

    /**
     * 退款金额
     */
    private Integer refundFee;

    /**
     * 退款货币种类
     */
    private String refundFeeType;

    /**
     * 退款原因
     */
    private String refundDesc;

    /**
     * 退款资金来源
     */
    private RefundAccountEnum refundAccount;

    /**
     * 退款结果通知url
     */
    private String notifyUrl;

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public String getRefundFeeType() {
        return refundFeeType;
    }

    public void setRefundFeeType(String refundFeeType) {
        this.refundFeeType = refundFeeType;
    }

    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
    }

    public RefundAccountEnum getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(RefundAccountEnum refundAccount) {
        this.refundAccount = refundAccount;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
