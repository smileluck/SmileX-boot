package top.zsmile.pay.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "返回客户端下单对象")
public class ReturnVO implements Serializable {
    private static final long serialVersionUID = 1L;

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

    /*************非通用返回**************/
    @ApiModelProperty(value = "二维码链接。trade_type=NATIVE 时有返回。时效性为2小时")
    private String codeUrl;
    @ApiModelProperty(value = "H5支付链接。trade_type=MWEB 时有返回。有效期为5分钟")
    private String mwebUrl;

    /*************针对类型返回**************/
    @ApiModelProperty(value = "JSAPI返回体封装")
    private JsApiPayVO jsApiPayVO;

    @ApiModelProperty(value = "APP支付返回体封装")
    private AppPayVO appPayVO;

    @ApiModelProperty(value = "付款码支付返回体封装。trade_type=MICROPAY返回")
    private MicroPayVO microPayVO;

    @ApiModelProperty(value = "查询订单返回体")
    private OrderQueryVO orderQueryVO;

    @ApiModelProperty(value = "退款订单返回体")
    private OrderRefundVO orderRefundVO;

    public static ReturnVO of(String returnCode, String returnMsg) {
        ReturnVO returnVO = new ReturnVO();
        returnVO.setReturnCode(returnCode);
        returnVO.setReturnMsg(returnMsg);
        return returnVO;
    }
}
