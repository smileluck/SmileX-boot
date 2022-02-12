package top.zsmile.modules.generator.model;

import lombok.Data;

import java.util.List;

@Data
public class TableModel {
    /**
     * 表名
     */
    private String name;
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
    /**
     * Entity实体变量
     */
    private List<EntityModel> entityModels;
}
