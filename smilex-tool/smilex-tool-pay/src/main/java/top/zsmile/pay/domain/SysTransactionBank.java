package top.zsmile.pay.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;

import java.io.Serializable;

/**
 * 支付系统银行类型对象 sys_transaction_bank
 *
 * @author B.Smile
 * @date 2024-03-29
 */
@TableName("sys_transaction_bank")
public class SysTransactionBank extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 银行类型
     */
    private Integer bankType;

    /**
     * 字符编码
     */
    private String codeFormat;

    /**
     * 卡类型
     */
    private String cardType;

    /**
     * 卡名称
     */
    private String bankName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBankType(Integer bankType) {
        this.bankType = bankType;
    }

    public Integer getBankType() {
        return bankType;
    }

    public void setCodeFormat(String codeFormat) {
        this.codeFormat = codeFormat;
    }

    public String getCodeFormat() {
        return codeFormat;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("bankType", getBankType())
                .append("codeFormat", getCodeFormat())
                .append("cardType", getCardType())
                .append("bankName", getBankName())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
