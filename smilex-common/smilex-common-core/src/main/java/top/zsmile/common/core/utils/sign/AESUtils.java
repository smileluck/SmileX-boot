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
    public static void main(String[] args) throws Exception {
//        String key = generatorKey();
//        System.out.println(key);

//        String str = "api_key=wandershare&id=101&mask_times={\n" +
//                "        \"0\":\"https://delete-temp-1317824441.cos.ap-guangzhou.myqcloud.com/mch-object-remove/masks/0_0.png\",\n" +
//                "        \"5.574171\":\"https://delete-temp-1317824441.cos.ap-guangzhou.myqcloud.com/mch-object-remove/masks/5.574171_0.png\"\n" +
//                "\t}&notify_url=http://test.com&time_stamp=1727668630000&video_url=http://alisz-cloud-storage-test.oss-cn-shenzhen.aliyuncs.com/pcloud/552088188/0/202406/1/vrw2B7Nvq2iHD1717233578.mp4?OSSAccessKeyId=LTAI5t7c9xsuxp5yDnYmnAeB&Expires=1727669621&Signature=fovPb8L6i7fWDU%2BWyCHmVy7Xt9M%3D";
//        String encode = encode(str, "0F82CB27C88DE28E94828B7D110086E8");
//        System.out.println(encode);
        String str2 = "api_key=wandershare&id=101&mask_times={\"0\":\"https://delete-temp-1317824441.cos.ap-guangzhou.myqcloud.com/mch-object-remove/masks/0_0.png\",\"5.574171\":\"https://delete-temp-1317824441.cos.ap-guangzhou.myqcloud.com/mch-object-remove/masks/5.574171_0.png\"\t}&notify_url=http://test.com&time_stamp=1727668630000&video_url=http://alisz-cloud-storage-test.oss-cn-shenzhen.aliyuncs.com/pcloud/552088188/0/202406/1/vrw2B7Nvq2iHD1717233578.mp4?OSSAccessKeyId=LTAI5t7c9xsuxp5yDnYmnAeB&Expires=1727669621&Signature=fovPb8L6i7fWDU%2BWyCHmVy7Xt9M%3D";
//
        String encode2 = encode2(str2, "0F82CB27C88DE28E94828B7D110086E8");
        System.out.println(encode2);

//        String s = MD5Utils.MD5(encode);
//        System.out.println(s);


//        String signUrl = "1y6OLt4E+txczoruJ6MjIXUG5YhlIncw6FleOJDigAKy8puttHJ3j/hQQX1ReVIpPieQ8UPTGx9cqU+UUK2grVXrKnbqi/56go6jyde5n5fTRAipL10achQPwUbcnGnfMnl0L7AaBqLgtxPKjsQ388P4M32gd+xnbLF4l2AioFU7hTxz0XhfrAAtsdRmYrLTIE/GdFKs09Syj9/RAqgbtjwznHm27TfmE4a/DaYJ9zysR36bbdSp5aTlXjwU89VzQVJitY2NVwsuXuh8qUt7qV+T8JrfbDap962GyocWZfBYxFEa5MWqSSD+bXb+Aqphdnot21tMSYt35+q4NvWZlkx2FJ9c3nz3d+QyTLBTsYCWCo4uep1SIripqW8aAMHhfA5LGYlBzadu5vN9yGR8utE/ZNNuHrfS8BG+zT4xNvSoH5uj4IivYXUyNQyZ6r0JtRTuTvsh2ftZ3ZSG2nEaXxrXoasihcvBnOv50fAbvvnzyGaWPLiM5vhNTuvpCrjrjQr5cGo7euiawur79mI5qUIyQ3oULshzgaWVCLZG2Mg=";
//
//        String decode = decode(signUrl, "3EEC80859648D9FB3A27C2EB3AEB8D0B");


//        String str2 = "U2FsdGVkX1+08tmxrlyMBdj4Zmvvj175j5PbGjA7Y897LWA9pKvxs7/BIdSON4f1guHdbRieQTvX5Rj/tqtf+AkmfWuDSSNuupokIAKbWzdOS2mO9asTpcwTYScgKPs5plv61kASsHnP60Z7AyKiT780gPr5jUg/aEBVShrhTx6C5XBHCCBCGKvF5mdl17Pdsg5GS6O1JVlbo+hBS9o5EFQmKbyTFL0AN4DSxx/ioMiAjDKsnEYpYJO3rzJRp2TCQIeOVj3BUZdrkRkDOkvTkhCY83ZYWZxMJfVpVucduC2rKYeBre7rejnHDlbl/IX31365zPsMfzDVO36k/tlQUFHxPjX2hP2cB5fHvzwwo+v71Cv2I3BBnWs8XC3b2QiLzDPlTdnqv6BaI8M7iKDZ7lpdlhIxc42mlTW3q7a3HTmPh49A7hjrCdtoLpcyCEg6RmxErGkyM0L4oQtZT2wpEgIqlpIBzYrR1x7wNSo5AEkMANCysPswLq0NzgDhMDCjL25t2rWp3Lcjk58wZpGVdi67e577ZYqe5uLB7T+Id+kS+nAoAou3ILsNR539O/WFPlu93e18VHNRGUbyjPtVJFYpMm4H7fyI6oxuUNHkJesDwknAAia4oyz461usD9ByjEgoLbABja3Zb24OywoBMidWAwInhpt2Di81f0hMYfJieyYhEwPz122rZCPTzfKzU13c5/XSE5n3YIH9cKf57Q5Yhuyd9b6aHOY5OazDyUw30STVZ1V8ZDRoCB03OVHKVzKm+HGsL8dN/o1kI3yG5Q==";
//        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
//        String s1 = Base64.encodeBase64String(bytes);
//        System.out.println(s1);
    }
}
