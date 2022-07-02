package top.zsmile.modules.generator.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class TableModel {
    /**
     * 包名
     */
    private String packages;
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
     * 横杠名称
     */
    private String smallDashName;
    /**
     * :名称
     */
    private String smallColonName;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 请求映射
     */
    private String reqMapping;
    /**
     * 表注释
     */
    private String tableComment;
    /**
     * 主键字段
     */
    private ColumnModel primaryColumn;
    /**
     * 逻辑删除字段
     */
    private ColumnModel logicDelColumn;
    /**
     * 字段列表,不包含主键字段
     */
    private List<ColumnModel> columnModels;
    /**
     * 菜单模型,用于生成SQL
     */
    private MenuModel menuModel;
}
