package top.zsmile.common.utils;

import lombok.SneakyThrows;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 签名
 */
public class MD5SignUtils {

    /**
     * 签名
     *
     * @param data
     * @param key
     * @param signType
     * @return
     * @throws Exception
     */
    @SneakyThrows
    public static String generateSignature(Map<String, String> data, String key, String signType) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Set<String> keySet = data.keySet();
        String[] keyArray = (String[]) keySet.toArray(new String[keySet.size()]);
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
        String str1 = sb.toString();
        sb.append("key=").append(key);
        if ("MD5".equals(signType)) {
            return MD5Utils.MD5(sb.toString()).toUpperCase();
        } else {
            throw new RuntimeException("签名类型错误");
        }
    }


    /**
     * 签名
     *
     * @param data
     * @param key
     * @param signType
     * @return
     * @throws Exception
     */
    public static Boolean vaildSignature(Map<String, String> data, String key, String signType) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String sign = generateSignature(data, key, signType);
        String checkSign = data.get("sign").toString();
        if (sign.equalsIgnoreCase(checkSign)) {
            return true;
        } else {
            return false;
        }
    }


    public static Boolean checkSignTime(String timestamp) {
        if (timestamp == null || timestamp.equalsIgnoreCase("")) {
            //logger.error("{}-鉴权时间戳缺失", MsgCode.SIGN_PARAM_ERROR_CODE);
            return false;
        }
        long currentTime = System.currentTimeMillis() / 1000;// 统一都传秒
        long requestTime = 0;
        try {
            requestTime = Long.parseLong(timestamp);
        } catch (NumberFormatException e) {
            //logger.error("{}-鉴权时间戳错误 timestamp:{}", MsgCode.SIGN_PARAM_ERROR_CODE, timestamp);
            return false;
        }
        if (Math.abs(currentTime - requestTime) > 120) {
//            logger.error("{}-签名已过期，服务器当前时间:{}", MsgCode.OUT_OF_DATE_SIGN_CODE, currentTime);
//            throw new BusinessException(String.format(MsgCode.OUT_OF_DATE_SIGN_MSG, currentTime), MsgCode.OUT_OF_DATE_SIGN_CODE);
            return false;
        }
        return true;
    }

}
