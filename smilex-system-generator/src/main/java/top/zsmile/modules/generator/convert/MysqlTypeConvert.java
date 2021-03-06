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

        convertMap.put("char", "String");
        convertMap.put("varchar", "String");
        convertMap.put("tinytext", "String");
        convertMap.put("text", "String");
        convertMap.put("mediumtext", "String");
        convertMap.put("longtext", "String");

        convertMap.put("date", "Date");
        convertMap.put("datetime", "Date");
        convertMap.put("timestamp", "Date");
    }

    @Override
    public String convert(String key) {
        return convertMap.get(key);
    }
}
