package top.zsmile.utils;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class AESUtil {
    // 加密算法
    private static final String ENCRY_ALGORITHM = "AES";
    // 加密算法/加密模式/填充类型
    private static final String CIPHER_MODE = "AES/ECB/PKCS5Padding";
    // 设置iv偏移量，ECB加密模式不需要设置 iv 偏移量
    private static final String IV = "0000000000000000";
    // 设置加密字符集
    private static final String CHARACTER = "UTF-8";
    // 加密密码长度。默认 16 byte * 8 = 128 bit
    private static final int PWD_SIZE = 16;

    /**
     * 随机生成秘钥
     */
    public static String generatorKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(ENCRY_ALGORITHM);
            kg.init(128);
            //要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            String key = CmdUtil.bytesToHexString(b);
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("没有此算法。");
            return null;
        }
    }

    /**
     * 解密
     *
     * @param content 密文
     * @param key     加密密码
     * @return String
     * @throws Exception 异常
     */
    public static String decode(String content, String key) {
        try {
            byte[] arr = CmdUtil.toBytes(content);
            byte[] raw = key.getBytes(CHARACTER);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, ENCRY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] original = cipher.doFinal(arr);
            return new String(original, CHARACTER);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密
     *
     * @param content 原文
     * @param key     加密密码
     * @return String
     * @throws Exception 异常
     */
    public static String encode(String content, String key) {
        try {
            byte[] raw = key.getBytes(CHARACTER);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, ENCRY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(CHARACTER));
            return CmdUtil.bytesToHexString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(generatorKey());
    }
}
