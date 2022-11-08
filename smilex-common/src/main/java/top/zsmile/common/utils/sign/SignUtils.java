package top.zsmile.common.utils.sign;

import com.alibaba.fastjson.JSONObject;
import top.zsmile.common.utils.CmdUtils;
import top.zsmile.core.exception.SXException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUtils {

    /**
     * Hmacsha256加密
     *
     * @param data 要加密的字符串
     * @return 加密后的字符串
     */
    public static String hmacSha256Hash(String key, String data) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
            return CmdUtils.bytesToHexString(array);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SXException("加密失败");
        }
    }

}
