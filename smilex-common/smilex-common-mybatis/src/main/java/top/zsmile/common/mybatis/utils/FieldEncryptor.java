package top.zsmile.common.mybatis.utils;

import lombok.extern.slf4j.Slf4j;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.mybatis.annotation.FieldEncrypt;
import top.zsmile.common.mybatis.meta.TableInfo;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 字段加解密工具（AES/GCM + Base64）
 * <p>
 * 密文格式：Base64(12字节IV + GCM密文)，随机 IV 保证同明文每次密文不同。
 * 全局密钥由配置注入（{@link #init(String)}），未初始化且存在加密字段时 fail-fast。
 */
@Slf4j
public final class FieldEncryptor {

    private static final int IV_LENGTH = 12;
    private static final int GCM_TAG_BITS = 128;
    private static final SecureRandom RANDOM = new SecureRandom();

    private static volatile byte[] globalKey;

    private FieldEncryptor() {
    }

    /**
     * 注入全局密钥（Base64 编码）
     */
    public static void init(String base64Key) {
        if (base64Key == null || base64Key.isEmpty()) {
            globalKey = null;
            return;
        }
        byte[] key = Base64.getDecoder().decode(base64Key);
        if (key.length != 16 && key.length != 24 && key.length != 32) {
            throw new SXException("smilex.mybatis.encrypt-key 长度非法，AES 密钥应为 16/24/32 字节");
        }
        globalKey = key;
    }

    /**
     * 加密实体上带 @FieldEncrypt 的 String 字段（值为空或已加密则跳过）
     */
    public static void encrypt(Object entity, TableInfo tableInfo) {
        if (entity == null || !tableInfo.hasEncryptField()) {
            return;
        }
        for (Field field : tableInfo.getEncryptFields()) {
            try {
                Object value = field.get(entity);
                if (value instanceof String && !((String) value).isEmpty() && !isEncrypted((String) value)) {
                    field.set(entity, encrypt((String) value, field.getAnnotation(FieldEncrypt.class)));
                }
            } catch (IllegalAccessException e) {
                throw new SXException("字段加密失败: " + tableInfo.getTableName() + "." + field.getName(), e);
            }
        }
    }

    /**
     * 解密实体上带 @FieldEncrypt 的 String 字段
     */
    public static void decrypt(Object entity, TableInfo tableInfo) {
        if (entity == null || !tableInfo.hasEncryptField()) {
            return;
        }
        for (Field field : tableInfo.getEncryptFields()) {
            try {
                Object value = field.get(entity);
                if (value instanceof String && isEncrypted((String) value)) {
                    field.set(entity, decrypt((String) value, field.getAnnotation(FieldEncrypt.class)));
                }
            } catch (IllegalAccessException e) {
                throw new SXException("字段解密失败: " + tableInfo.getTableName() + "." + field.getName(), e);
            }
        }
    }

    /**
     * 判断值是否已是本工具产生的密文（Base64 且 IV+密文长度合法，仅作启发式跳过）
     */
    private static boolean isEncrypted(String value) {
        try {
            byte[] bytes = Base64.getDecoder().decode(value);
            return bytes.length > IV_LENGTH;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static String encrypt(String plain, FieldEncrypt annotation) {
        try {
            byte[] key = resolveKey(annotation);
            Cipher cipher = Cipher.getInstance(algorithm(annotation));
            byte[] iv = new byte[IV_LENGTH];
            RANDOM.nextBytes(iv);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(GCM_TAG_BITS, iv));
            byte[] cipherText = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
            byte[] result = new byte[IV_LENGTH + cipherText.length];
            System.arraycopy(iv, 0, result, 0, IV_LENGTH);
            System.arraycopy(cipherText, 0, result, IV_LENGTH, cipherText.length);
            return Base64.getEncoder().encodeToString(result);
        } catch (SXException e) {
            throw e;
        } catch (Exception e) {
            throw new SXException("字段加密失败", e);
        }
    }

    private static String decrypt(String cipherTextBase64, FieldEncrypt annotation) {
        try {
            byte[] key = resolveKey(annotation);
            byte[] all = Base64.getDecoder().decode(cipherTextBase64);
            if (all.length <= IV_LENGTH) {
                return cipherTextBase64;
            }
            Cipher cipher = Cipher.getInstance(algorithm(annotation));
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_BITS, all, 0, IV_LENGTH);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), spec);
            byte[] plain = cipher.doFinal(all, IV_LENGTH, all.length - IV_LENGTH);
            return new String(plain, StandardCharsets.UTF_8);
        } catch (SXException e) {
            throw e;
        } catch (Exception e) {
            log.warn("字段解密失败，返回原值: {}", e.getMessage());
            return cipherTextBase64;
        }
    }

    private static String algorithm(FieldEncrypt annotation) {
        return annotation == null ? "AES/GCM/NoPadding" : annotation.algorithm();
    }

    private static byte[] resolveKey(FieldEncrypt annotation) {
        if (annotation != null && !annotation.key().isEmpty()) {
            return Base64.getDecoder().decode(annotation.key());
        }
        byte[] key = globalKey;
        if (key == null) {
            throw new SXException("未配置字段加密密钥 smilex.mybatis.encrypt-key，无法加解密 @FieldEncrypt 字段");
        }
        return key;
    }
}
