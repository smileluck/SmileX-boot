package top.zsmile.common.core.utils.sign;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.core.utils.CmdUtils;
import top.zsmile.common.core.utils.PasswordUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

@Slf4j
public class AESUtils {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    // 加密算法
    private static final String ENCRY_ALGORITHM = "AES";
    // 加密算法/加密模式/填充类型
    private static final String CIPHER_MODE = "AES/CBC/PKCS7Padding";
    // 设置iv偏移量，ECB加密模式不需要设置 iv 偏移量
    private static final String IV = "1234567890tapall";
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
            String key = CmdUtils.bytesToHexString(b);
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("没有此算法。");
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
            byte[] arr = CmdUtils.toBytes(content);
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
//            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(CHARACTER));
            return CmdUtils.bytesToHexString(encrypted);
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
    public static String encode2(String content, String key) {
        try {
            byte[] raw = key.getBytes(CHARACTER);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, ENCRY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);//"算法/模式/补码方式"
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec,paramSpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(CHARACTER));

//            System.out.println(Base64.encodeBase64String(encrypted));
            return sha256Hash(Base64.encodeBase64String(encrypted));
//            return sha256Hash(encrypted);
//            return DigestUtils.sha256Hex("4444".getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * sha256加密
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String sha256Hash(String bytes) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes.getBytes(StandardCharsets.UTF_8));
//            System.out.println(Base64.encodeBase64String(messageDigest.digest()));
            return CmdUtils.bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new SXException("加密失败");
        }
    }
    /**
     * sha256加密
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String sha256Hash(byte[] bytes) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes);
//            System.out.println(Base64.encodeBase64String(messageDigest.digest()));
            return CmdUtils.bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new SXException("加密失败");
        }
    }
}
