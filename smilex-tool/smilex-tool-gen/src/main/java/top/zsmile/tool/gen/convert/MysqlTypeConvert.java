package top.zsmile.tool.gen.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * MySQL 类型 -> Java 类型转换
 * <p>
 * 未命中的类型兜底为 String（原实现返回 null 会导致 FTL 渲染抛异常整表生成失败）；
 * tinyint(1) 约定映射 Boolean（依据 column_type 判定，information_schema 的 data_type 不含显示宽度）。
 */
@Slf4j
@Component
public class MysqlTypeConvert implements TypeConverter {

    private static final Map<String, String> CONVERT_MAP;

    static {
        CONVERT_MAP = new HashMap<>();
        // 数值
        CONVERT_MAP.put("tinyint", "Integer");
        CONVERT_MAP.put("smallint", "Integer");
        CONVERT_MAP.put("mediumint", "Integer");
        CONVERT_MAP.put("int", "Integer");
        CONVERT_MAP.put("integer", "Integer");
        CONVERT_MAP.put("bigint", "Long");
        CONVERT_MAP.put("float", "Float");
        CONVERT_MAP.put("double", "Double");
        CONVERT_MAP.put("decimal", "BigDecimal");
        CONVERT_MAP.put("numeric", "BigDecimal");
        // 位
        CONVERT_MAP.put("bit", "Boolean");
        // 字符串
        CONVERT_MAP.put("char", "String");
        CONVERT_MAP.put("varchar", "String");
        CONVERT_MAP.put("tinytext", "String");
        CONVERT_MAP.put("text", "String");
        CONVERT_MAP.put("mediumtext", "String");
        CONVERT_MAP.put("longtext", "String");
        CONVERT_MAP.put("enum", "String");
        CONVERT_MAP.put("set", "String");
        CONVERT_MAP.put("json", "String");
        // 时间
        CONVERT_MAP.put("date", "LocalDate");
        CONVERT_MAP.put("datetime", "LocalDateTime");
        CONVERT_MAP.put("timestamp", "LocalDateTime");
        CONVERT_MAP.put("time", "LocalTime");
        CONVERT_MAP.put("year", "Integer");
        // 二进制
        CONVERT_MAP.put("binary", "byte[]");
        CONVERT_MAP.put("varbinary", "byte[]");
        CONVERT_MAP.put("tinyblob", "byte[]");
        CONVERT_MAP.put("blob", "byte[]");
        CONVERT_MAP.put("mediumblob", "byte[]");
        CONVERT_MAP.put("longblob", "byte[]");
    }

    @Override
    public String convert(String dataType) {
        return convert(dataType, null);
    }

    @Override
    public String convert(String dataType, String columnType) {
        if (dataType == null || dataType.isEmpty()) {
            return "String";
        }
        String key = dataType.toLowerCase(Locale.ENGLISH);
        // tinyint(1) 约定为布尔
        if ("tinyint".equals(key) && columnType != null && columnType.startsWith("tinyint(1)")) {
            return "Boolean";
        }
        String javaType = CONVERT_MAP.get(key);
        if (javaType == null) {
            log.warn("未知数据库类型[{}]，兜底映射为 String", dataType);
            return "String";
        }
        return javaType;
    }
}
