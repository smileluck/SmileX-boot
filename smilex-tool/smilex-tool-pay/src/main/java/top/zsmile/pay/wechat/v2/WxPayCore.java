package top.zsmile.pay.wechat.v2;


import top.zsmile.pay.constant.WxV2Constant;
import top.zsmile.pay.enums.SignTypeEnum;
import top.zsmile.pay.wechat.v2.config.WxPayConfig;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class WxPayCore {
    private WxPayConfig config;
    private SignTypeEnum signType;
    private boolean useSandbox;//仿真测试系统

    public static WxPayCore of(WxPayConfig config) {
        return of(config, SignTypeEnum.MD5, false);
    }

    public static WxPayCore of(WxPayConfig config, SignTypeEnum signType) {
        return of(config, signType, false);
    }

    public static WxPayCore of(WxPayConfig config, SignTypeEnum signType, boolean useSandbox) {
        return new WxPayCore(config, signType, useSandbox);
    }

    private WxPayCore(WxPayConfig config, SignTypeEnum signType, boolean useSandbox) {
        this.config = config;
        this.signType = signType;
        this.useSandbox = useSandbox;
    }

    /***
     * 通用补全加密方法
     * @param reqData
     * @return
     * @throws Exception
     */
    public Map<String, String> commonFillRequestData(Map<String, String> reqData) throws Exception {
        reqData.put(WxV2Constant.FIELD_APP_ID, this.config.getAppID());
        reqData.put(WxV2Constant.FIELD_MCH_ID, this.config.getMchID());
        reqData.put(WxV2Constant.FIELD_NONCE_STR, WxPayUtil.generateNonceStr());
        if (this.config.getMchType() == 2) {//服务商
            reqData.put(WxV2Constant.FIELD_SUB_APP_ID, this.config.getSubAppID());
            reqData.put(WxV2Constant.FIELD_SUB_MCH_ID, this.config.getSubMchID());
        }

        if (SignTypeEnum.MD5.equals(this.signType)) {
            reqData.put(WxV2Constant.FIELD_SIGN_TYPE, SignTypeEnum.MD5.getValue());
        } else if (SignTypeEnum.HMACSHA256.equals(this.signType)) {
            reqData.put(WxV2Constant.FIELD_SIGN_TYPE, SignTypeEnum.HMACSHA256.getValue());
        }

        reqData.put(WxV2Constant.FIELD_SIGN, WxPayUtil.generateSignature(reqData, this.config.getKey(), this.signType));
        return reqData;
    }


    /**
     * 商户补全加密方法
     *
     * @param reqData
     * @return
     * @throws Exception
     */
    public Map<String, String> mchFillRequestData(Map<String, String> reqData) throws Exception {
        reqData.put("mch_appid", this.config.getAppID());
        reqData.put("mchid", this.config.getMchID());
        reqData.put(WxV2Constant.FIELD_NONCE_STR, WxPayUtil.generateNonceStr());


        if (SignTypeEnum.MD5.equals(this.signType)) {
            reqData.put(WxV2Constant.FIELD_SIGN_TYPE, SignTypeEnum.MD5.getValue());
        } else if (SignTypeEnum.HMACSHA256.equals(this.signType)) {
            reqData.put(WxV2Constant.FIELD_SIGN_TYPE, SignTypeEnum.HMACSHA256.getValue());
        }

        reqData.put(WxV2Constant.FIELD_SIGN, WxPayUtil.generateSignature(reqData, this.config.getKey(), this.signType));
        return reqData;
    }


    public Map<String, String> processResponseXml(String xmlStr) throws Exception {
        String RETURN_CODE = "return_code";
        Map<String, String> respData = WxPayUtil.xmlToMap(xmlStr);
        if (respData.containsKey(RETURN_CODE)) {
            String return_code = (String) respData.get(RETURN_CODE);
            if (return_code.equals(WxV2Constant.FAIL)) {
                return respData;
            } else if (return_code.equals(WxV2Constant.SUCCESS)) {
                if (this.isResponseSignatureValid(respData)) {
                    return respData;
                } else {
                    throw new Exception(String.format("Invalid sign value in XML: %s", xmlStr));
                }
            } else {
                throw new Exception(String.format("return_code value %s is invalid in XML: %s", return_code, xmlStr));
            }
        } else {
            throw new Exception(String.format("No `return_code` in XML: %s", xmlStr));
        }
    }

    public boolean isResponseSignatureValid(Map<String, String> reqData) throws Exception {
        return WxPayUtil.isSignatureValid(reqData, this.config.getKey(), this.signType);
    }


    public boolean isPayResultNotifySignatureValid(Map<String, String> reqData) throws Exception {
        String signTypeInData = (String) reqData.get(WxV2Constant.FIELD_SIGN_TYPE);
        SignTypeEnum signType;
        if (signTypeInData == null) {
            signType = SignTypeEnum.MD5;
        } else {
            signTypeInData = signTypeInData.trim();
            if (signTypeInData.length() == 0) {
                signType = SignTypeEnum.MD5;
            } else if (SignTypeEnum.MD5.getValue().equals(signTypeInData)) {
                signType = SignTypeEnum.MD5;
            } else {
                if (!SignTypeEnum.HMACSHA256.getValue().equals(signTypeInData)) {
                    throw new Exception(String.format("Unsupported sign_type: %s", signTypeInData));
                }

                signType = SignTypeEnum.HMACSHA256;
            }
        }

        return WxPayUtil.isSignatureValid(reqData, this.config.getKey(), signType);
    }

    public String requestWithoutCert(String strUrl, Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String UTF8 = "UTF-8";
        String reqBody = WxPayUtil.mapToXml(reqData);
        URL httpUrl = new URL(strUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(connectTimeoutMs);
        httpURLConnection.setReadTimeout(readTimeoutMs);
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(reqBody.getBytes(UTF8));
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }

        String resp = stringBuffer.toString();
        if (stringBuffer != null) {
            try {
                bufferedReader.close();
            } catch (IOException var18) {
                var18.printStackTrace();
            }
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException var17) {
                var17.printStackTrace();
            }
        }

        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException var16) {
                var16.printStackTrace();
            }
        }

        return resp;
    }

    public String requestWithCert(String strUrl, Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String UTF8 = "UTF-8";
        String reqBody = WxPayUtil.mapToXml(reqData);
        URL httpUrl = new URL(strUrl);
        char[] password = this.config.getMchID().toCharArray();
        InputStream certStream = this.config.getCertStream();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(certStream, password);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), (TrustManager[]) null, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(connectTimeoutMs);
        httpURLConnection.setReadTimeout(readTimeoutMs);
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(reqBody.getBytes(UTF8));
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }

        String resp = stringBuffer.toString();
        if (stringBuffer != null) {
            try {
                bufferedReader.close();
            } catch (IOException var24) {
                ;
            }
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException var23) {
                ;
            }
        }

        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException var22) {
                ;
            }
        }

        if (certStream != null) {
            try {
                certStream.close();
            } catch (IOException var21) {
                ;
            }
        }

        return resp;
    }


    public Map<String, String> microPay(Map<String, String> reqData) throws Exception {
        return this.microPay(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> microPay(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_MICROPAY_URL;
        } else {
            url = WxV2Constant.MICROPAY_URL;
        }

        String respXml = this.requestWithoutCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    public Map<String, String> unifiedOrder(Map<String, String> reqData) throws Exception {
        return this.unifiedOrder(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> unifiedOrder(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_UNIFIEDORDER_URL;
        } else {
            url = WxV2Constant.UNIFIEDORDER_URL;
        }

        String respXml = this.requestWithoutCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    public Map<String, String> orderQuery(Map<String, String> reqData) throws Exception {
        return this.orderQuery(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> orderQuery(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_ORDERQUERY_URL;
        } else {
            url = WxV2Constant.ORDERQUERY_URL;
        }

        String respXml = this.requestWithoutCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    public Map<String, String> reverse(Map<String, String> reqData) throws Exception {
        return this.reverse(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> reverse(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_REVERSE_URL;
        } else {
            url = WxV2Constant.REVERSE_URL;
        }

        String respXml = this.requestWithCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    public Map<String, String> closeOrder(Map<String, String> reqData) throws Exception {
        return this.closeOrder(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> closeOrder(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_CLOSEORDER_URL;
        } else {
            url = WxV2Constant.CLOSEORDER_URL;
        }

        String respXml = this.requestWithoutCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    public Map<String, String> refund(Map<String, String> reqData) throws Exception {
        return this.refund(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> refund(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_REFUND_URL;
        } else {
            url = WxV2Constant.REFUND_URL;
        }

        String respXml = this.requestWithCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    public Map<String, String> refundQuery(Map<String, String> reqData) throws Exception {
        return this.refundQuery(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> refundQuery(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_REFUNDQUERY_URL;
        } else {
            url = WxV2Constant.REFUNDQUERY_URL;
        }

        String respXml = this.requestWithoutCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    public Map<String, String> downloadBill(Map<String, String> reqData) throws Exception {
        return this.downloadBill(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> downloadBill(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_DOWNLOADBILL_URL;
        } else {
            url = WxV2Constant.DOWNLOADBILL_URL;
        }

        String respStr = this.requestWithoutCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs).trim();
        Object ret;
        if (respStr.indexOf("<") == 0) {
            ret = WxPayUtil.xmlToMap(respStr);
        } else {
            ret = new HashMap();
            ((Map) ret).put("return_code", "SUCCESS");
            ((Map) ret).put("return_msg", "ok");
            ((Map) ret).put("data", respStr);
        }

        return (Map) ret;
    }

    public Map<String, String> report(Map<String, String> reqData) throws Exception {
        return this.report(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> report(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_REPORT_URL;
        } else {
            url = WxV2Constant.REPORT_URL;
        }

        String respXml = this.requestWithoutCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return WxPayUtil.xmlToMap(respXml);
    }

    public Map<String, String> shortUrl(Map<String, String> reqData) throws Exception {
        return this.shortUrl(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> shortUrl(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_SHORTURL_URL;
        } else {
            url = WxV2Constant.SHORTURL_URL;
        }

        String respXml = this.requestWithoutCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    public Map<String, String> authCodeToOpenid(Map<String, String> reqData) throws Exception {
        return this.authCodeToOpenid(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> authCodeToOpenid(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_AUTHCODETOOPENID_URL;
        } else {
            url = WxV2Constant.AUTHCODETOOPENID_URL;
        }

        String respXml = this.requestWithoutCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    public Map<String, String> transfersWallet(Map<String, String> reqData) throws Exception {
        return this.transfersWallet(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> transfersWallet(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_TRANSFERSWALLET_URL;
        } else {
            url = WxV2Constant.TRANSFERSWALLET_URL;
        }

        String respXml = this.requestWithCert(url, this.mchFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    public Map<String, String> transfersWalletInfo(Map<String, String> reqData) throws Exception {
        return this.transfersWalletInfo(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> transfersWalletInfo(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxV2Constant.SANDBOX_TRANSFERSWALLETINFO_URL;
        } else {
            url = WxV2Constant.TRANSFERSWALLETINFO_URL;
        }

        String respXml = this.requestWithCert(url, this.commonFillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }
}
