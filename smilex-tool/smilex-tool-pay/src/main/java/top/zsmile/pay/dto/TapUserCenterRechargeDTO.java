package top.zsmile.pay.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 用户中心-积分充值
 */
public class TapUserCenterRechargeDTO {

    @NotBlank(message = "请选择支付类型")
    private String payType;

    @Min(value = 10, message = "支付金额不能小于10元")
    @NotNull(message = "请输入支付金额")
    private BigDecimal money;


    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
