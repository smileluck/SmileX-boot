package top.zsmile.common.core.utils;

import top.zsmile.core.exception.SXException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtils {

    /**
     * 获取加密算法中使用的盐值,解密中使用的盐值必须与加密中使用的相同才能完成操作. 盐长度必须为8字节
     *
     * @return byte[] 盐值
     */
    public static byte[] getSalt() throws Exception {
        // 实例化安全随机数
        SecureRandom random = new SecureRandom();
        // 产出盐
        return random.generateSeed(20);
    }

    /**
     * sha256加密
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String sha256Hash(String str) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            return CmdUtils.bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new SXException("加密失败");
        }
    }
}
