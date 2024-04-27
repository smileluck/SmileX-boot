package top.zsmile.pay.bean;

/**
 * 微信V3应答
 */
public class WxV3Resp {

    public static final String RES_SUCCESS = "success";

    /**
     * 错误码，SUCCESS为清算机构接收成功，其他错误码为失败。
     */
    private String code;

    /**
     * 返回信息，如非空，为错误原因。
     */
    private String message;

    public WxV3Resp() {
    }

    public static WxV3Resp success() {
        return success(null);
    }

    public static WxV3Resp success(String message) {
        WxV3Resp wxV3Resp = new WxV3Resp();
        wxV3Resp.code = "SUCCESS";
        wxV3Resp.message = message;
        return wxV3Resp;
    }

    public static WxV3Resp fail(String message) {
        WxV3Resp wxV3Resp = new WxV3Resp();
        wxV3Resp.code = "FAIL";
        wxV3Resp.message = message;
        return wxV3Resp;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
