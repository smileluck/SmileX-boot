package top.zsmile.tool.gen.utils;

import top.zsmile.common.core.constant.StringConstant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 命名转换工具
 * <p>
 * 修复连续下划线（user__name -> userName）；Java 关键字列名追加下划线后缀（order_ 之外的 class/int 等）。
 */
public class NameStyleUtils {
    private static Pattern linePattern = Pattern.compile("_+(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * Java 关键字（生成属性名时规避）
     */
    private static final Set<String> JAVA_KEYWORDS = new HashSet<>(Arrays.asList(
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
            "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
            "volatile", "while", "true", "false", "null"));

    public static String lineToHump(String str, boolean state) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        str = str.toLowerCase();
        StringBuffer sb = new StringBuffer();

        Matcher matcher = linePattern.matcher(str);
        while (matcher.find()) {
            // 连续下划线折叠：_+x -> X
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        String result = sb.toString();
        if (state && !result.isEmpty()) {
            result = Character.toUpperCase(result.charAt(0)) + result.substring(1);
        }
        // Java 关键字规避：追加下划线后缀
        if (JAVA_KEYWORDS.contains(result)) {
            result = result + "_";
        }
        return result;
    }

    public static String humpToLine(String str) {
        StringBuffer sb = new StringBuffer();

        Matcher matcher = humpPattern.matcher(str);
        while (matcher.find()) {
            if (sb.length() != 0) {
                matcher.appendReplacement(sb, StringConstant.UNDERSCORE + matcher.group(0));
            } else {
                matcher.appendReplacement(sb, matcher.group(0));
            }
        }
        matcher.appendTail(sb);

        return sb.toString().toLowerCase();
    }

    public static String lineToSlash(String str) {
        return lineToCustomStr(str, StringConstant.SLASH);
    }

    public static String lineToDash(String str) {
        return lineToCustomStr(str, StringConstant.DASH);
    }

    public static String lineToCustomStr(String str, String replacestr) {
        str = str.toLowerCase();
        StringBuffer sb = new StringBuffer();

        Matcher matcher = linePattern.matcher(str);
        while (matcher.find()) {
            matcher.appendReplacement(sb, replacestr + matcher.group(1));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
