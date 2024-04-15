package top.zsmile.pay.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wechat.pay.java.core.Config;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import top.zsmile.common.mybatis.annotation.TableId;
import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付系统对象 sys_transaction
 *
 * @author B.Smile
 * @date 2024-03-29
 */
@TableName("sys_transaction")
public class SysTransaction extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户订单ID
     */
    private String orderNo;

    /**
     * 支付系统订单ID
     */
    private String outOrderNo;

    /**
     * 三方用户id
     */
    private String openid;

    /**
     * 公众号/应用id
     */
    private String appid;

    /**
     * 商户id
     */
    private String mchid;

    /**
     * 用户支付币种
     */
    private String payCurrency;

    /**
     * 支付场景描述
     */
    private String sceneInfo;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 处理方式
     */
    private String handleType;
    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 交易状态
     */
    private String tradeState;

    /**
     * 交易状态描述
     */
    private String tradeStateDesc;

    /**
     * 银行类型
     */
    private String bankType;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品总价
     */
    private BigDecimal amount;

    /**
     * 应收价格
     */
    private BigDecimal recePrice;

    /**
     * 实收价格
     */
    private BigDecimal realPrice;

    /**
     * 已退款金额
     */
    private BigDecimal refundPrice;

    /**
     * 费率
     */
    private BigDecimal rate;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime expireTime;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime payTime;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime successTime;

    /**
     * 通知地址
     */
    private transient String notifyUrl;

    /**
     * 配置
     */
    private transient Config config;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setPayCurrency(String payCurrency) {
        this.payCurrency = payCurrency;
    }

    public String getPayCurrency() {
        return payCurrency;
    }

    public void setSceneInfo(String sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public String getSceneInfo() {
        return sceneInfo;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBankType() {
        return bankType;
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

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setRecePrice(BigDecimal recePrice) {
        this.recePrice = recePrice;
    }

    public BigDecimal getRecePrice() {
        return recePrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRefundPrice(BigDecimal refundPrice) {
        this.refundPrice = refundPrice;
    }

    public BigDecimal getRefundPrice() {
        return refundPrice;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public LocalDateTime getPayTime() {
        return payTime;
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
                .append("orderNo", getOrderNo())
                .append("outOrderNo", getOutOrderNo())
                .append("openid", getOpenid())
                .append("appid", getAppid())
                .append("mchid", getMchid())
                .append("payCurrency", getPayCurrency())
                .append("sceneInfo", getSceneInfo())
                .append("payType", getPayType())
                .append("tradeType", getTradeType())
                .append("tradeState", getTradeState())
                .append("tradeStateDesc", getTradeStateDesc())
                .append("bankType", getBankType())
                .append("quantity", getQuantity())
                .append("price", getPrice())
                .append("amount", getAmount())
                .append("recePrice", getRecePrice())
                .append("realPrice", getRealPrice())
                .append("refundPrice", getRefundPrice())
                .append("rate", getRate())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("expireTime", getExpireTime())
                .append("payTime", getPayTime())
                .append("successTime", getSuccessTime())
                .append("delFlag", getDelFlag())
                .toString();
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getHandleType() {
        return handleType;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }
}
