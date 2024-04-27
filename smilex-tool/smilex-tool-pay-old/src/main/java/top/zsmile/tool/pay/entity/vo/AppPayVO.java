package top.zsmile.tool.pay.entity.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class AppPayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
    private String appId;
    /**
     * 商户号
     */
    private String partnerId;
    /**
     * 时间戳
     */
    private String prepayId;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 随机串
     */
    private String nonceStr;
    /**
     * 扩展字段
     */
    private String packageStr;
    /**
     * 签名
     */
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
