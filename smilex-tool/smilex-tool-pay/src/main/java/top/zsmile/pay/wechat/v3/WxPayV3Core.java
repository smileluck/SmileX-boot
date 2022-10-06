package top.zsmile.pay.wechat.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import top.zsmile.pay.enums.SignTypeEnum;
import top.zsmile.pay.wechat.v2.config.WxPayConfig;
import top.zsmile.pay.wechat.v2.WxPayUtil;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class WxPayV3Core {
    private WxPayConfig config;
    private SignTypeEnum signType;
    private boolean useSandbox;//仿真测试系统
    private CloseableHttpClient httpClient;

    public WxPayV3Core(WxPayConfig config) {
        this(config, SignTypeEnum.MD5, false);
    }

    public WxPayV3Core(WxPayConfig config, SignTypeEnum signType) {
        this(config, signType, false);
    }

    public WxPayV3Core(WxPayConfig config, SignTypeEnum signType, boolean useSandbox) {
        this.config = config;
        this.signType = signType;
        this.useSandbox = useSandbox;

//        CertificatesManager certificatesManager = CertificatesManager.getInstance();
//        certificatesManager.putMerchant(mchId, new WechatPay2Credentials(mchId,
//                new PrivateKeySigner(mchSerialNo, merchantPrivateKey)), apiV3Key.getBytes(StandardCharsets.UTF_8));
//        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create();
//        this.httpClient = builder.build();
    }


    public Map<String, String> payTransactionsApp(Map<String, String> reqData) throws Exception {
        return this.payTransactionsApp(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    public Map<String, String> payTransactionsApp(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WxPayV3Constant.SANDBOX_APP_V3_PREPAY_URL;
        } else {
            url = WxPayV3Constant.APP_V3_PREPAY_URL;
        }

        String respXml = this.requestWithoutCert(url, connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    public boolean isResponseSignatureValid(Map<String, String> reqData) throws Exception {
        return WxPayUtil.isSignatureValid(reqData, this.config.getKey(), this.signType);
    }


    public Map<String, String> processResponseXml(String xmlStr) throws Exception {
        String RETURN_CODE = "return_code";
        Map<String, String> respData = WxPayUtil.xmlToMap(xmlStr);
        if (respData.containsKey(RETURN_CODE)) {
            String return_code = (String) respData.get(RETURN_CODE);
            if (return_code.equals("FAIL")) {
                return respData;
            } else if (return_code.equals("SUCCESS")) {
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

    public String requestWithoutCert(String strUrl, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String UTF8 = "UTF-8";
//        String reqBody = WxPayUtil.mapToXml(reqData);
//        String reqBody = JSON.toJSONString(reqData);


        HttpPost httpPost = new HttpPost(strUrl);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("appid", this.config.getAppID())
                .put("mchid", this.config.getMchID())
                .put("description", "Image形象店-深圳腾大-QQ公仔")
                .put("notify_url", "https://www.weixin.qq.com/wxpay/pay.php")
                .put("out_trade_no", "1217752501201407033233368018");
        rootNode.putObject("amount")
                .put("total", 1).put("currency", 1);

        objectMapper.writeValue(bos, rootNode);
        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));

        CloseableHttpResponse response = httpClient.execute(httpPost);

        String bodyAsString = EntityUtils.toString(response.getEntity());
        System.out.println(bodyAsString);
//        URL httpUrl = new URL(strUrl);
//        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
//        httpURLConnection.setDoOutput(true);
//        httpURLConnection.setRequestMethod("POST");
//        httpURLConnection.setConnectTimeout(connectTimeoutMs);
//        httpURLConnection.setReadTimeout(readTimeoutMs);
//        httpURLConnection.connect();
//        OutputStream outputStream = httpURLConnection.getOutputStream();
//        outputStream.write(reqBody.getBytes(UTF8));
//        InputStream inputStream = httpURLConnection.getInputStream();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
//        StringBuffer stringBuffer = new StringBuffer();
//        String line = null;
//
//        while ((line = bufferedReader.readLine()) != null) {
//            stringBuffer.append(line);
//        }
//
//        String resp = stringBuffer.toString();
//        if (stringBuffer != null) {
//            try {
//                bufferedReader.close();
//            } catch (IOException var18) {
//                var18.printStackTrace();
//            }
//        }
//
//        if (inputStream != null) {
//            try {
//                inputStream.close();
//            } catch (IOException var17) {
//                var17.printStackTrace();
//            }
//        }
//
//        if (outputStream != null) {
//            try {
//                outputStream.close();
//            } catch (IOException var16) {
//                var16.printStackTrace();
//            }
//        }
//
//        return resp;
        return null;
    }
//
//    /***
//     * 通用补全加密方法
//     * @param reqData
//     * @return
//     * @throws Exception
//     */
//    public Map<String, String> commonFillRequestData(ObjectNode reqData) throws Exception {
//        reqData.put("appid", this.config.getAppID());
//        reqData.put("mchid", this.config.getMchID());
//        reqData.put("nonce_str", WxPayUtil.generateNonceStr());
//        if (this.config.getMchType() == 2) {//服务商
//            reqData.put("sub_appid", this.config.getSubAppID());
//            reqData.put("sub_mch_id", this.config.getSubMchID());
//        }
//
//        if (SignTypeEnum.MD5.equals(this.signType)) {
//            reqData.put("sign_type", "MD5");
//        } else if (SignTypeEnum.HMACSHA256.equals(this.signType)) {
//            reqData.put("sign_type", "HMAC-SHA256");
//        }
//
//        reqData.put("sign", WxPayUtil.generateSignature(reqData, this.config.getKey(), this.signType));
//        return reqData;
//    }


}
