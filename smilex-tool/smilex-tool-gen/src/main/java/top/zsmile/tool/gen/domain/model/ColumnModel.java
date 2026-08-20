package top.zsmile.tool.gen.domain.model;

import lombok.Data;

@Data
public class ColumnModel {
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 小驼峰字段名
     */
    private String humpColumnName;
    /**
     * 大驼峰字段名
     */
    private String bigHumpColumnName;
    /**
     * 完整字段类型（如 tinyint(1) unsigned，用于类型判定）
     */
    private String columnType;
    /**
     * 数据类型（如 tinyint）
     */
    private String dataType;
    /**
     * convert后的java类型
     */
    private String convertDataType;
    /**
     * 字段注释
     */
    private String columnComment;
    /**
     * 字段Key类型（PRI/UNI/MUL...）
     */
    private String columnKey;
    /**
     * 附加信息（auto_increment/on update 等）
     */
    private String extra;
}
