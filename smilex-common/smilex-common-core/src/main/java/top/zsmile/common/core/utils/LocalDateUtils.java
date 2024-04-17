package top.zsmile.common.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateUtils {

    // 使用RFC 3339格式将ZonedDateTime对象格式化为字符串
    public static final String FORMAT_RFC3339 = "yyyy-MM-dd'T'HH:mm:ss.ssXXX";

    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化成字符串
     *
     * @param localDateTime 时间
     * @param format        格式
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static LocalDateTime parse(String time, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, formatter);
    }
}
