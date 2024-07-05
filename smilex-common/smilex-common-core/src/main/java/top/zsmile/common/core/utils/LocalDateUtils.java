package top.zsmile.common.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class LocalDateUtils {


    public static final String FORMAT_YYYY = "yyyy";

    public static final String FORMAT_YYYY_MM = "yyyy-MM";

    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

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

    /**
     * 转化为对象
     *
     * @param time   时间字符串
     * @param format 格式
     * @return
     */
    public static LocalDateTime parse(String time, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, formatter);
    }

    public static String dateTimeNow() {
        return dateTimeNow(FORMAT_YYYYMMDDHHMMSS);
    }

    public static String dateTimeNow(final String format) {
        return format(LocalDateTime.now(), format);
    }

}
