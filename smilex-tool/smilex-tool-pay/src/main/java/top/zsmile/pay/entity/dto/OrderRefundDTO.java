package top.zsmile.pay.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.zsmile.pay.enums.RefundAccountEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单退款
 */
@Data
@ApiModel(value = "订单退款数据对象")
public class OrderRefundDTO extends OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户退款单号")
    private String outRefundNo;

    @ApiModelProperty(value = "订单金额")
    private Integer totalFee;

    @ApiModelProperty(value = "退款金额")
    private Integer refundFee;

    @ApiModelProperty(value = "退款货币种类")
    private String refundFeeType;

    @ApiModelProperty(value = "退款原因")
    private String refundDesc;

    @ApiModelProperty(value = "退款资金来源")
    private RefundAccountEnum refundAccount;

    @ApiModelProperty(value = "退款结果通知url")
    private String notifyUrl;
}
