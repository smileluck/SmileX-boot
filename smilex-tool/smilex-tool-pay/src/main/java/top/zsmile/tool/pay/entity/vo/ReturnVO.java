package top.zsmile.tool.pay.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReturnVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 返回状态码
     */
    private String returnCode;

    /**
     * 返回信息
     */
    private String returnMsg;

    /*************return_code为SUCCESS的时候有返回**************/

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 子商户应用ID
     */
    private String subAppId;

    /**
     * 商户ID
     */
    private String mchId;

    /**
     * 子商户ID
     */
    private String subMchId;

    /**
     * 设备号
     */
    private String deviceInfo;

    /**
     * 随机字符串
     */
    private String nonceStr;

    /**
     * 签名
     */
    private String sign;

    /**
     * 业务结果。SUCCESS/FAIL
     */
    private String resultCode;

    /**
     * 错误代码。当result_code为FAIL时返回错误代码
     */
    private String errCode;

    /**
     * 错误代码描述。当result_code为FAIL时返回错误代码
     */
    private String errCodeDes;

    /*************return_code和result_code都为SUCCESS**************/
    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 预支付交易会话标识。有效期为2小时
     */
    private String prepayId;

    /*************非通用返回**************/
    /**
     * 二维码链接。trade_type=NATIVE 时有返回。时效性为2小时
     */
    private String codeUrl;
    /**
     * H5支付链接。trade_type=MWEB 时有返回。有效期为5分钟
     */
    private String mwebUrl;

    /*************针对类型返回**************/
    /**
     * JSAPI返回体封装
     */
    private JsApiPayVO jsApiPayVO;

    /**
     * APP支付返回体封装
     */
    private AppPayVO appPayVO;

    /**
     * 付款码支付返回体封装。trade_type=MICROPAY返回
     */
    private MicroPayVO microPayVO;

    /**
     * 查询订单返回体
     */
    private OrderQueryVO orderQueryVO;

    /**
     * 退款订单返回体
     */
    private OrderRefundVO orderRefundVO;

    public static ReturnVO of(String returnCode, String returnMsg) {
        ReturnVO returnVO = new ReturnVO();
        returnVO.setReturnCode(returnCode);
        returnVO.setReturnMsg(returnMsg);
        return returnVO;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getMwebUrl() {
        return mwebUrl;
    }

    public void setMwebUrl(String mwebUrl) {
        this.mwebUrl = mwebUrl;
    }

    public JsApiPayVO getJsApiPayVO() {
        return jsApiPayVO;
    }

    public void setJsApiPayVO(JsApiPayVO jsApiPayVO) {
        this.jsApiPayVO = jsApiPayVO;
    }

    public AppPayVO getAppPayVO() {
        return appPayVO;
    }

    public void setAppPayVO(AppPayVO appPayVO) {
        this.appPayVO = appPayVO;
    }

    public MicroPayVO getMicroPayVO() {
        return microPayVO;
    }

    public void setMicroPayVO(MicroPayVO microPayVO) {
        this.microPayVO = microPayVO;
    }

    public OrderQueryVO getOrderQueryVO() {
        return orderQueryVO;
    }

    public void setOrderQueryVO(OrderQueryVO orderQueryVO) {
        this.orderQueryVO = orderQueryVO;
    }

    public OrderRefundVO getOrderRefundVO() {
        return orderRefundVO;
    }

    public void setOrderRefundVO(OrderRefundVO orderRefundVO) {
        this.orderRefundVO = orderRefundVO;
    }
}
