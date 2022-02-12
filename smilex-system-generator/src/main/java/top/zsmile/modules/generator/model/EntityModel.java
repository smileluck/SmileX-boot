package top.zsmile.modules.generator.model;

import lombok.Data;

@Data
public class EntityModel {
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 字段类型
     */
    private String columnType;
    /**
     * 字段注释
     */
    private String columnComment;
}
