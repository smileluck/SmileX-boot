package top.zsmile.pay.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单退款查询
 */
@Data
@ApiModel(value = "订单退款查询数据对象")
public class OrderRefundQueryDTO extends OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户退款单号")
    private String outRefundNo;

    @ApiModelProperty(value = "微信退款单号")
    private String refundId;

    @ApiModelProperty(value = "偏移量")
    private Integer offset;
}
