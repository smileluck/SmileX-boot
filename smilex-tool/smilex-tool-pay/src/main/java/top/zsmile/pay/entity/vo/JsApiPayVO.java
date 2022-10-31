package top.zsmile.pay.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@ApiModel(value = "JsApi支付返回体分装")
public class JsApiPayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用ID")
    private String appId;
    @ApiModelProperty(value = "时间戳")
    private String timeStamp;
    @ApiModelProperty(value = "随机串")
    private String nonceStr;
    @ApiModelProperty(value = "数据包")
    private String packageStr;
    @ApiModelProperty(value = "签名方式")
    private String signType;
    @ApiModelProperty(value = "签名")
    private String paySign;

    public static JsApiPayVO of(Map<String, String> map) {
        JsApiPayVO jsApiPayVO = new JsApiPayVO();
        jsApiPayVO.setAppId(map.get("appId"));
        jsApiPayVO.setPaySign(map.get("paySign"));
        jsApiPayVO.setNonceStr(map.get("nonceStr"));
        jsApiPayVO.setTimeStamp(map.get("timeStamp"));
        jsApiPayVO.setSignType(map.get("signType"));
        jsApiPayVO.setPackageStr(map.get("package"));
        return jsApiPayVO;
    }
}
