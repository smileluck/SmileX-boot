package top.zsmile.pay.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@ApiModel(value = "JsApi支付返回体分装")
public class AppPayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用ID")
    private String appId;
    @ApiModelProperty(value = "商户号")
    private String partnerId;
    @ApiModelProperty(value = "时间戳")
    private String prepayId;
    @ApiModelProperty(value = "时间戳")
    private String timeStamp;
    @ApiModelProperty(value = "随机串")
    private String nonceStr;
    @ApiModelProperty(value = "扩展字段")
    private String packageStr;
    @ApiModelProperty(value = "签名")
    private String sign;

    public static AppPayVO of(Map<String, String> map) {
        AppPayVO appPayVO = new AppPayVO();
        appPayVO.setAppId(map.get("appid"));
        appPayVO.setPartnerId(map.get("partnerid"));
        appPayVO.setPrepayId(map.get("prepayid"));
        appPayVO.setNonceStr(map.get("noncestr"));
        appPayVO.setTimeStamp(map.get("timestamp"));
        appPayVO.setPackageStr(map.get("package"));
        appPayVO.setSign(map.get("sign"));
        return appPayVO;
    }
}
