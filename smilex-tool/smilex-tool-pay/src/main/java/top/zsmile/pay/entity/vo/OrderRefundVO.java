package top.zsmile.pay.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 退款订单
 */
@Data
@ApiModel(value = "退款订单返回对象")
public class OrderRefundVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信支付订单号")
    private String transactionId;
    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;
    @ApiModelProperty(value = "微信退款单号")
    private String refundId;
    @ApiModelProperty(value = "退款金额")
    private Integer refundFee;
    @ApiModelProperty(value = "应结订单金额")
    private Integer settlementRefundFee;
    @ApiModelProperty(value = "标价金额")
    private Integer totalFee;
    @ApiModelProperty(value = "应结订单金额")
    private Integer settlementTotalFee;
    @ApiModelProperty(value = "货币类型")
    private String feeType;
    @ApiModelProperty(value = "现金支付货币类型")
    private Integer cashFee;
    @ApiModelProperty(value = "现金支付货币类型")
    private String cashFeeType;
    @ApiModelProperty(value = "现金退款金额")
    private Integer cashRefundFee;
    @ApiModelProperty(value = "代金券退款总金额")
    private Integer couponRefundFee;
    @ApiModelProperty(value = "退款代金券使用数量")
    private Integer couponRefundCount;

    @ApiModelProperty(value = "退款代金券列表")
    private List<CouponItemVO> couponList;

    public static OrderRefundVO of(Map<String, String> map) {
        OrderRefundVO vo = new OrderRefundVO();
        vo.setTransactionId(map.get("transaction_id"));
        vo.setOutTradeNo(map.get("out_trade_no"));
        vo.setRefundId(map.get("refund_id"));
        vo.setRefundFee(Integer.valueOf(map.get("refund_fee")));
        vo.setSettlementRefundFee(Integer.valueOf(map.get("settlement_refund_fee")));
        vo.setTotalFee(Integer.valueOf(map.get("total_fee")));
        vo.setSettlementTotalFee(Integer.valueOf(map.get("settlement_total_fee")));
        vo.setFeeType(map.get("fee_type"));
        vo.setCashFee(Integer.valueOf(map.get("cash_fee")));
        vo.setCashFeeType(map.get("cash_fee_type"));
        vo.setCouponRefundFee(Integer.valueOf(map.get("coupon_refund_fee")));
        vo.setCouponRefundCount(Integer.valueOf(map.get("coupon_refund_count")));
        Set<String> keySet = map.keySet();
        long couponCount = keySet.stream().filter(item -> item.startsWith("coupon_type_")).count();
        if (couponCount > 0) {
            List<CouponItemVO> list = new ArrayList<>();
            for (long i = 0; i < couponCount; i++) {
                CouponItemVO itemVO = CouponItemVO.of(map.get("coupon_refund_id_" + i), map.get("coupon_type_" + i), Integer.valueOf(map.get("coupon_refund_fee_" + i)));
                list.add(itemVO);
            }
            vo.setCouponList(list);
        }
        return vo;
    }

}
