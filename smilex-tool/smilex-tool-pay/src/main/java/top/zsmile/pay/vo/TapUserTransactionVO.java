package top.zsmile.pay.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

public class TapUserTransactionVO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 订单ID
     */
    private Long transactionId;
    /**
     * phone
     */
//    @Excel(name = "手机号")
    private String phone;

    /**
     * 订单类型
     */
//    @Excel(name = "订单类型")
    private String orderType;
    /**
     * 商户订单ID
     */
//    @Excel(name = "商户订单ID")
    private String orderNo;

    /**
     * 支付系统订单ID
     */
//    @Excel(name = "支付系统订单ID")
    private String outOrderNo;

    /**
     * 支付场景描述
     */
//    @Excel(name = "支付场景描述")
    private String sceneInfo;

    /**
     * 支付方式
     */
//    @Excel(name = "支付方式")
    private String payType;

    /**
     * 处理方式
     */
//    @Excel(name = "处理方式")
    private String handleType;
    /**
     * 交易类型
     */
//    @Excel(name = "交易类型")
    private String tradeType;

    /**
     * 交易状态
     */
//    @Excel(name = "交易状态")
    private String tradeState;


    /**
     * 商品数量
     */
//    @Excel(name = "商品数量")
    private Integer quantity;

    /**
     * 商品价格
     */
//    @Excel(name = "商品价格")
    private BigDecimal price;

    /**
     * 商品总价
     */
//    @Excel(name = "商品总价")
    private BigDecimal amount;

    /**
     * 应收价格
     */
//    @Excel(name = "应收价格")
    private BigDecimal recePrice;

    /**
     * 实收价格
     */
//    @Excel(name = "实收价格")
    private BigDecimal realPrice;

    /**
     * 已退款金额
     */
//    @Excel(name = "已退款金额")
    private BigDecimal refundPrice;

    /**
     * 费率
     */
//    @Excel(name = "费率")
    private BigDecimal rate;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date successTime;

    /**
     * 创建时间
     */
//    @Excel(name = "下单时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(String sceneInfo) {
        this.sceneInfo = sceneInfo;
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

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRecePrice() {
        return recePrice;
    }

    public void setRecePrice(BigDecimal recePrice) {
        this.recePrice = recePrice;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public BigDecimal getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(BigDecimal refundPrice) {
        this.refundPrice = refundPrice;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
