package top.zsmile.pay.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import top.zsmile.common.mybatis.annotation.TableId;
import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付系统退款对象 sys_transaction_refund
 *
 * @author B.Smile
 * @date 2024-03-29
 */
@TableName("sys_transaction_refund")
public class SysTransactionRefund extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 退款订单
     */
    private String refundNo;

    /**
     * 支付系统退款订单
     */
    private String outRefundNo;

    /**
     * 退款状态
     */
    private String refundStatus;

    /**
     * 退款单的退款入账方
     */
    private String userReceivedAccount;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 退款金额
     */
    private BigDecimal price;

    /**
     * 退款渠道
     */
    private String channel;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime successTime;

    /**
     * transaction Id
     */
    private Long transactionId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setUserReceivedAccount(String userReceivedAccount) {
        this.userReceivedAccount = userReceivedAccount;
    }

    public String getUserReceivedAccount() {
        return userReceivedAccount;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setSuccessTime(LocalDateTime successTime) {
        this.successTime = successTime;
    }

    public LocalDateTime getSuccessTime() {
        return successTime;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("refundNo", getRefundNo())
                .append("outRefundNo", getOutRefundNo())
                .append("refundStatus", getRefundStatus())
                .append("userReceivedAccount", getUserReceivedAccount())
                .append("quantity", getQuantity())
                .append("price", getPrice())
                .append("channel", getChannel())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("successTime", getSuccessTime())
                .append("delFlag", getDelFlag())
                .toString();
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
