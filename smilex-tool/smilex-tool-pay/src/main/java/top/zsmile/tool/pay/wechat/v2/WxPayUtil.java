package top.zsmile.tool.pay.wechat.v2;

import top.zsmile.tool.pay.constant.WxV2Constant;
import top.zsmile.tool.pay.entity.vo.JsApiPayVO;
import top.zsmile.tool.pay.entity.vo.MicroPayVO;
import top.zsmile.tool.pay.entity.vo.ReturnVO;
import top.zsmile.tool.pay.enums.SignTypeEnum;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import top.zsmile.tool.pay.wechat.v2.config.WxPayConfig;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 微信支付工具
 */
public class WxPayUtil {


    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        Map<String, String> data = new HashMap();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
        Document doc = documentBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == 1) {
                Element element = (Element) node;
                data.put(element.getNodeName(), element.getTextContent());
            }
        }

        try {
            stream.close();
        } catch (Exception var10) {
            ;
        }

        return data;
    }

    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("xml");
        document.appendChild(root);
        Iterator var5 = data.keySet().iterator();

        while (var5.hasNext()) {
            String key = (String) var5.next();
            String value = (String) data.get(key);
            if (value == null) {
                value = "";
            }

            value = value.trim();
            Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty("encoding", "UTF-8");
        transformer.setOutputProperty("indent", "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString();

        try {
            writer.close();
        } catch (Exception var12) {
            ;
        }

        return output;
    }

    public static String generateSignedXml(Map<String, String> data, String key) throws Exception {
        return generateSignedXml(data, key, SignTypeEnum.MD5);
    }

    public static String generateSignedXml(Map<String, String> data, String key, SignTypeEnum signType) throws Exception {
        String sign = generateSignature(data, key, signType);
        data.put("sign", sign);
        return mapToXml(data);
    }

    public static boolean isSignatureValid(String xmlStr, String key) throws Exception {
        Map<String, String> data = xmlToMap(xmlStr);
        if (!data.containsKey("sign")) {
            return false;
        } else {
            String sign = (String) data.get("sign");
            return generateSignature(data, key).equals(sign);
        }
    }

    public static boolean isSignatureValid(Map<String, String> data, String key) throws Exception {
        return isSignatureValid(data, key, SignTypeEnum.MD5);
    }

    public static boolean isSignatureValid(Map<String, String> data, String key, SignTypeEnum signType) throws Exception {
        if (!data.containsKey("sign")) {
            return false;
        } else {
            String sign = (String) data.get("sign");
            return generateSignature(data, key, signType).equals(sign);
        }
    }

    public static String generateSignature(Map<String, String> data, String key) throws Exception {
        return generateSignature(data, key, SignTypeEnum.MD5);
    }

    public static String generateSignature(Map<String, String> data, String key, SignTypeEnum signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        String[] var6 = keyArray;
        int var7 = keyArray.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            String k = var6[var8];
            if (!k.equals("sign") && ((String) data.get(k)).trim().length() > 0) {
                sb.append(k).append("=").append(((String) data.get(k)).trim()).append("&");
            }
        }

        sb.append("key=").append(key);
        if (SignTypeEnum.MD5.equals(signType)) {
            return MD5(sb.toString()).toUpperCase();
        } else if (SignTypeEnum.HMACSHA256.equals(signType)) {
            return HMACSHA256(sb.toString(), key);
        } else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }

    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        byte[] var4 = array;
        int var5 = array.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            byte item = var4[var6];
            sb.append(Integer.toHexString(item & 255 | 256).substring(1, 3));
        }

        return sb.toString().toUpperCase();
    }

    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        byte[] var6 = array;
        int var7 = array.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            byte item = var6[var8];
            sb.append(Integer.toHexString(item & 255 | 256).substring(1, 3));
        }

        return sb.toString().toUpperCase();
    }

    public static Boolean checkResponseState(Map<String, String> resp) {
        if ("SUCCESS".equals(resp.get("return_code"))) {
            if (resp.containsKey("result_code")) {
                if ("SUCCESS".equals(resp.get("result_code"))) {
                    return true;
                }
            } else {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public static Boolean checkResponseState(ReturnVO vo) {
        if ("SUCCESS".equals(vo.getReturnCode())) {
            if ("SUCCESS".equals(vo.getReturnCode())) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public static ReturnVO mapToResult(WxPayConfig config, Map<String, String> resp) {
        ReturnVO vo = ReturnVO.of(resp.get(WxV2Constant.FIELD_RETURN_CODE), resp.get(WxV2Constant.FIELD_RETURN_MSG));
        if (vo.getReturnCode().equals(WxV2Constant.SUCCESS)) {
            vo.setAppId(resp.get(WxV2Constant.FIELD_APP_ID));
            vo.setMchId(resp.get(WxV2Constant.FIELD_MCH_ID));
            vo.setDeviceInfo(resp.get(WxV2Constant.FIELD_DEVICE_INFO));
            vo.setNonceStr(resp.get(WxV2Constant.FIELD_NONCE_STR));
            vo.setSign(resp.get(WxV2Constant.FIELD_SIGN));

            String resultCode = resp.get(WxV2Constant.FIELD_RESULT_CODE);
            vo.setResultCode(resultCode);
            if (WxV2Constant.SUCCESS.equals(resultCode)) {
                vo.setTradeType(resp.get(WxV2Constant.FIELD_TRADE_TYPE));
                vo.setPrepayId(resp.get(WxV2Constant.FIELD_PREPAY_ID));
                switch (vo.getTradeType()) {
                    case "NATIVE":// 微信通过扫一扫支付
                        vo.setCodeUrl(resp.get(WxV2Constant.FIELD_CODE_URL));
                        break;
                    case "MWEB":// H5 支付
                        vo.setMwebUrl(resp.get(WxV2Constant.FIELD_MWEB_URL));
                        break;
                    case "MICROPAY": // 付款码支付
                        vo.setMicroPayVO(MicroPayVO.of(resp));
                        break;
                }
            } else {
                vo.setErrCode(resp.get(WxV2Constant.FIELD_ERR_CODE));
                vo.setErrCodeDes(resp.get(WxV2Constant.FIELD_ERR_CODE_DES));
            }
        }
        return vo;
    }


    /**
     * 包装并签名返回给JSAPI调用的实体
     *
     * @return
     */
    public static JsApiPayVO packageJsApiResult(WxPayConfig config, ReturnVO returnVO) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("package", "prepay_id=" + returnVO.getPrepayId());
        if (!config.isMch()) {
            map.put("appId", returnVO.getAppId());
        } else {
            map.put("appId", returnVO.getSubAppId());
        }
        map.put("timeStamp", LocalDateTime.now().getNano() / 1000 + "");
        map.put("nonceStr", returnVO.getNonceStr());
        String signature = WxPayUtil.generateSignature(map, config.getKey());
        map.put("paySign", signature);
        return JsApiPayVO.of(map);
    }

}