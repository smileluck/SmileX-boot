package top.zsmile.tool.gen.convert;

public interface TypeConverter {

    /**
     * 数据库类型转 Java 类型
     *
     * @param dataType information_schema 的 data_type（如 varchar）
     * @return Java 类型名，未知类型兜底 String
     */
    String convert(String dataType);

    /**
     * 数据库类型转 Java 类型（带完整列类型，用于 tinyint(1) 等判定）
     *
     * @param dataType   data_type（如 tinyint）
     * @param columnType column_type（如 tinyint(1) unsigned）
     * @return Java 类型名，未知类型兜底 String
     */
    String convert(String dataType, String columnType);
}
