package top.zsmile.common.utils;

import org.apache.commons.lang3.StringUtils;

public class Asserts {

    /**
     * 检查对象是否为Null
     *
     * @param object  对象
     * @param message 异常消息
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检查对象是否不为Null
     *
     * @param object  对象
     * @param message 异常消息
     */
    public static void isNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检查字符串不为空
     *
     * @param str     字符串
     * @param message 异常消息
     */
    public static void isBlank(String str, String message) {
        if (StringUtils.isNotBlank(str)) {
            throw new IllegalArgumentException(message);
        }
    }
    /**
     * 检查字符串不为空
     *
     * @param str     字符串
     * @param message 异常消息
     */
    public static void isNotBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException(message);
        }
    }
}
