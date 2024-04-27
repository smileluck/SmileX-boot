package top.zsmile.tool.pay.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class JsApiPayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
    private String appId;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 随机串
     */
    private String nonceStr;
    /**
     * 数据包
     */
    private String packageStr;
    /**
     * 签名方式
     */
    private String signType;
    /**
     * 签名
     */
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
