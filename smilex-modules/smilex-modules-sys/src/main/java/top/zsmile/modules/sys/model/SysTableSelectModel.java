package top.zsmile.modules.sys.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 表查询模型
 */
@Data
@ApiModel(value = "表查询模型", description = "表查询模型")
public class SysTableSelectModel {
    /**
     * 查询表
     */
    @NotBlank(message = "查询表不能为空")
    private String dictTable;
    /**
     * 字段值
     */
    @NotBlank(message = "字段值不能为空")
    private String dictValue;
    /**
     * 字段显示
     */
    @NotBlank(message = "字段显示不能为空")
    private String dictLabel;
}
