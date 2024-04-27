package top.zsmile.tool.pay.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 查询订单
 */
@Data
public class OrderQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 设备号
     */
    private String deviceInfo;
    /**
     * 用户标识
     */
    private String openid;
    /**
     * is_subscribe。用户是否关注公众账号，仅在公众账号类型支付有效，取值范围：Y或N;Y-关注;N-未关注
     */
    private String subscribeState;
    /**
     * 交易状态
     */
    private String tradeState;
    /**
     * 付款银行
     */
    private String bankType;
    /**
     * 订单金额
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
     * 代金券金额
     */
    private Integer couponFee;
    /**
     * 代金券使用数量
     */
    private Integer couponCount;
    /**
     * 微信支付订单号
     */
    private String transactionId;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 商家数据包
     */
    private String attach;
    /**
     * 支付完成时间
     */
    private String timeEnd;
    /**
     * 交易状态描述
     */
    private String tradeStateDesc;
    /**
     * 使用代金卷情况
     */
    private List<CouponItemVO> couponList;

    public static OrderQueryVO of(Map<String, String> map) {
        OrderQueryVO vo = new OrderQueryVO();
        vo.setDeviceInfo(map.get("device_info"));
        vo.setOpenid(map.get("openid"));
        vo.setSubscribeState(map.get("is_subscribe"));
        vo.setTradeState(map.get("trade_state"));
        vo.setBankType(map.get("bank_type"));
        vo.setTotalFee(Integer.valueOf(map.get("total_fee")));
        vo.setSettlementTotalFee(Integer.valueOf(map.get("settlement_total_fee")));
        vo.setFeeType(map.get("fee_type"));
        vo.setCashFee(Integer.valueOf(map.get("cash_fee")));
        vo.setCashFeeType(map.get("cash_fee_type"));
        vo.setCouponFee(Integer.valueOf(map.get("coupon_fee")));
        vo.setCouponCount(Integer.valueOf(map.get("coupon_count")));
        vo.setTransactionId(map.get("transaction_id"));
        vo.setOutTradeNo(map.get("out_trade_no"));
        vo.setAttach(map.get("attach"));
        vo.setTimeEnd(map.get("time_end"));
        vo.setTradeStateDesc(map.get("trade_state_desc"));
        Set<String> keySet = map.keySet();
        long couponCount = keySet.stream().filter(item -> item.startsWith("coupon_type_")).count();
        if (couponCount > 0) {
            List<CouponItemVO> list = new ArrayList<>();
            for (long i = 0; i < couponCount; i++) {
                CouponItemVO itemVO = CouponItemVO.of(map.get("coupon_id_" + i), map.get("coupon_type_" + i), Integer.valueOf(map.get("coupon_fee_" + i)));
                list.add(itemVO);
            }
            vo.setCouponList(list);
        }
        return vo;
    }

}
