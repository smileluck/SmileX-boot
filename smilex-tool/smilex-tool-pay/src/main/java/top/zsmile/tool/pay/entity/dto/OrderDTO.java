package top.zsmile.tool.pay.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询订单
 */
@Data
@ApiModel(value = "查询订单数据对象")
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信订单号。建议优先使用")
    private String transactionId;

    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;

}
