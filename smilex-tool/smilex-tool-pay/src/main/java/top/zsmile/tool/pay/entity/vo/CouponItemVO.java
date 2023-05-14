package top.zsmile.tool.pay.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 代金券
 */
@Data
@ApiModel(value = "代金券列表对象")
public class CouponItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代金券类型")
    private String couponType;

    @ApiModelProperty(value = "代金券ID")
    private String couponId;

    @ApiModelProperty(value = "单个代金券支付金额")
    private Integer couponFee;

    public static CouponItemVO of(String couponId, String couponType, Integer couponFee) {
        CouponItemVO couponItemVO = new CouponItemVO();
        couponItemVO.setCouponId(couponId);
        couponItemVO.setCouponType(couponType);
        couponItemVO.setCouponFee(couponFee);
        return couponItemVO;
    }
}
