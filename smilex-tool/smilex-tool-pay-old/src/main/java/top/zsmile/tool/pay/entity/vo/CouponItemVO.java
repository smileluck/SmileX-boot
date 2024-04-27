package top.zsmile.tool.pay.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 代金券
 */
@Data
public class CouponItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 代金券类型
     */
    private String couponType;

    /**
     * 代金券ID
     */
    private String couponId;

    /**
     * 单个代金券支付金额
     */
    private Integer couponFee;

    public static CouponItemVO of(String couponId, String couponType, Integer couponFee) {
        CouponItemVO couponItemVO = new CouponItemVO();
        couponItemVO.setCouponId(couponId);
        couponItemVO.setCouponType(couponType);
        couponItemVO.setCouponFee(couponFee);
        return couponItemVO;
    }
}
