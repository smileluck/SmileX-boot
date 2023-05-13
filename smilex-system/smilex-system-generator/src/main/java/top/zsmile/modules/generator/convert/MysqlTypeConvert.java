package top.zsmile.modules.generator.convert;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MysqlTypeConvert implements TypeConverter {
    private static final Map<String, String> convertMap;

    static {
        convertMap = new HashMap<>();
        convertMap.put("tinyint", "Integer");
        convertMap.put("smallint", "Integer");
        convertMap.put("mediumint", "Integer");
        convertMap.put("int", "Integer");
        convertMap.put("integer", "Integer");
        convertMap.put("bigint", "Long");
        convertMap.put("float", "Float");
        convertMap.put("double", "Double");
        convertMap.put("decimal", "BigDecimal");
        convertMap.put("bit", "Boolean");

        // 字符串类型
        convertMap.put("char", "String");
        convertMap.put("varchar", "String");
        convertMap.put("tinytext", "String");
        convertMap.put("text", "String");
        convertMap.put("mediumtext", "String");
        convertMap.put("longtext", "String");

        // 时间类型
        convertMap.put("date", "LocalDateTime");
        convertMap.put("datetime", "LocalDateTime");
        convertMap.put("timestamp", "LocalDateTime");
    }

    @Override
    public String convert(String key) {
        return convertMap.get(key);
    }
}
