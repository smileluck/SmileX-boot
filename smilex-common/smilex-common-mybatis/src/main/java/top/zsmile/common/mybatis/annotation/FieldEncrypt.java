package top.zsmile.common.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段加密注解
 * <p>
 * 标注在 String 字段上：写入时 AES/GCM 加密后 Base64 存储，读取时自动解密。
 * 全局密钥通过 smilex.mybatis.encrypt-key（Base64）配置；未配置密钥时启动遇到加密字段将报错。
 * 注意：加密发生在实体值替换层面，保存后内存中的实体将持有密文。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldEncrypt {

    /**
     * 加密算法，默认 AES/GCM/NoPadding
     */
    String algorithm() default "AES/GCM/NoPadding";

    /**
     * 覆盖全局密钥（Base64），留空使用全局配置
     */
    String key() default "";
}
