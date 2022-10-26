package top.zsmile.pay.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "返回客户端对象")
public class ReturnVO {
    @ApiModelProperty(value = "返回状态码")
    private String returnCode;

    @ApiModelProperty(value = "返回信息")
    private String returnMsg;

    /*************return_code为SUCCESS的时候有返回**************/

    @ApiModelProperty(value = "应用ID")
    private String appId;

    @ApiModelProperty(value = "商户ID")
    private String mchId;

    @ApiModelProperty(value = "设备号")
    private String deviceInfo;

    @ApiModelProperty(value = "随机字符串")
    private String nonceStr;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "业务结果。SUCCESS/FAIL")
    private String resultCode;

    @ApiModelProperty(value = "错误代码。当result_code为FAIL时返回错误代码")
    private String errCode;

    @ApiModelProperty(value = "错误代码描述。当result_code为FAIL时返回错误代码")
    private String errCodeDes;

    /*************return_code和result_code都为SUCCESS**************/
    @ApiModelProperty(value = "交易类型")
    private String tradeType;

    @ApiModelProperty(value = "预支付交易会话标识。有效期为2小时")
    private String prepayId;

    @ApiModelProperty(value = "二维码链接。trade_type=NATIVE时有返回。时效性为2小时")
    private String codeUrl;

    public static ReturnVO of(String returnCode, String returnMsg) {
        ReturnVO returnVO = new ReturnVO();
        returnVO.setReturnCode(returnCode);
        returnVO.setReturnMsg(returnMsg);
        return returnVO;
    }
}
