package top.zsmile.tool.pay.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class MicroPayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户标识
     */
    private String openid;
    /**
     * is_subscribe。用户是否关注公众账号，仅在公众账号类型支付有效，取值范围：Y或N;Y-关注;N-未关注
     */
    private String subscribeState;
    /**
     * 付款银行
     */
    private String bankType;
    /**
     * 货币类型
     */
    private String feeType;
    /**
     * 订单金额
     */
    private Integer totalFee;
    /**
     * 应结订单金额
     */
    private Integer settlementTotalFee;
    /**
     * 代金券金额
     */
    private Integer couponFee;
    /**
     * 现金支付货币类型
     */
    private String cashFeeType;
    /**
     * 现金支付货币类型
     */
    private Integer cashFee;
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
     * 营销详情
     */
    private String promotionDetail;

    public static MicroPayVO of(Map<String, String> map) {
        MicroPayVO microPayVO = new MicroPayVO();
        microPayVO.setOpenid(map.get("openid"));
        microPayVO.setSubscribeState(map.get("is_subscribe"));
        microPayVO.setBankType(map.get("bank_type"));
        microPayVO.setFeeType(map.get("fee_type"));
        microPayVO.setTotalFee(Integer.valueOf(map.get("total_fee")));
        microPayVO.setSettlementTotalFee(Integer.valueOf(map.get("settlement_total_fee")));
        microPayVO.setCouponFee(Integer.valueOf(map.get("coupon_fee")));
        microPayVO.setCashFeeType(map.get("cash_fee_type"));
        microPayVO.setCashFee(Integer.valueOf(map.get("cash_fee")));
        microPayVO.setTransactionId(map.get("transaction_id"));
        microPayVO.setOutTradeNo(map.get("out_trade_no"));
        microPayVO.setAttach(map.get("attach"));
        microPayVO.setTimeEnd(map.get("time_end"));
        microPayVO.setPromotionDetail(map.get("promotion_detail"));
        return microPayVO;
    }
}
