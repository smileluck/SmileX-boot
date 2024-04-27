package top.zsmile.tool.pay.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 退款订单
 */
@Data
public class OrderRefundVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 微信支付订单号
     */
    private String transactionId;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 微信退款单号
     */
    private String refundId;
    /**
     * 退款金额
     */
    private Integer refundFee;
    /**
     * 应结订单金额
     */
    private Integer settlementRefundFee;
    /**
     * 标价金额
     */
    private Integer totalFee;
    /**
     * 应结订单金额
     */
    private Integer settlementTotalFee;
    /**
     * 货币类型
     */
    private String feeType;
    /**
     * 现金支付货币类型
     */
    private Integer cashFee;
    /**
     * 现金支付货币类型
     */
    private String cashFeeType;
    /**
     * 现金退款金额
     */
    private Integer cashRefundFee;
    /**
     * 代金券退款总金额
     */
    private Integer couponRefundFee;
    /**
     * 退款代金券使用数量
     */
    private Integer couponRefundCount;

    /**
     * 退款代金券列表
     */
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
