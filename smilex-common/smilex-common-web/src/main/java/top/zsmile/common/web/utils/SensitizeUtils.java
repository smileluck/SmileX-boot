package top.zsmile.common.web.utils;

/**
 * 脱敏工具类
 *
 * @author B.Smile
 * @date 2024-04-19
 */
public class SensitizeUtils {
    /**
     * 脱敏
     *
     * @param value  待脱敏的值
     * @param start  开始位置
     * @param length 脱敏长度
     * @return 脱敏后的值
     */
    public static String desensitize(String value, int start, int length) {
        if (value == null || value.length() == 0) {
            return value;
        }
        int end = start + length;
        if (end > value.length()) {
            end = value.length();
        }
        return value.substring(0, start) + "****" + value.substring(end);
    }

    /**
     * 手机号脱敏
     *
     * @param phone 手机号
     * @return 脱敏后的手机号
     */
    public static String desensitizePhone(String phone) {
        if (phone == null || phone.length() == 0) {
            return phone;
        }
        return desensitize(phone, 3, 4);
    }

}
