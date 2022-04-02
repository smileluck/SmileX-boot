package top.zsmile.modules.generator.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class TableModel {
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 大驼峰类名
     */
    private String bigHumpClass;
    /**
     * 小驼峰类名
     */
    private String smallHumpClass;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表注释
     */
    private String tableComment;
    /**
     * 主键字段
     */
    private ColumnModel primaryColumn;
    /**
     * 字段列表,不包含主键字段
     */
    private List<ColumnModel> columnModels;
}
