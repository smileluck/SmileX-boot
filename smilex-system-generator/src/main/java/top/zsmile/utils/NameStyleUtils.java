package top.zsmile.utils;


import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameStyleUtils {
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static String lineToHump(String str, boolean state) {
        str = str.toLowerCase();
        StringBuffer sb = new StringBuffer();

        Matcher matcher = linePattern.matcher(str);
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        if (state) {
            sb.replace(0, 1, String.valueOf(sb.charAt(0)).toUpperCase());
        }

        return sb.toString();
    }

    public static String humpToLine(String str) {
        StringBuffer sb = new StringBuffer();

        Matcher matcher = humpPattern.matcher(str);
        while (matcher.find()) {
            if (sb.length() != 0) {
                matcher.appendReplacement(sb, StringPool.UNDERSCORE + matcher.group(0));
            } else {
                matcher.appendReplacement(sb, matcher.group(0));
            }
        }
        matcher.appendTail(sb);


        return sb.toString().toLowerCase();
    }

    public static String lineToSlash(String str) {
        str = str.toLowerCase();
        StringBuffer sb = new StringBuffer();

        Matcher matcher = linePattern.matcher(str);
        while (matcher.find()) {
            matcher.appendReplacement(sb, StringPool.SLASH + matcher.group(1));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
