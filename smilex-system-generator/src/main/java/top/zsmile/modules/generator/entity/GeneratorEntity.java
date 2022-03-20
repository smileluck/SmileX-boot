package top.zsmile.modules.generator.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class GeneratorEntity implements Serializable {
    private static final long serialVersionUID = -5033545272962428672L;
    /**
     * 包路径
     */
    @NotBlank(message = "包路径不能为空")
    private String packagePath;
    /**
     * 模块名
     */
    @NotBlank(message = "模块名不能为空")
    private String moduleName;
    /**
     * 表名
     */
    @NotBlank(message = "请选择表")
    private String tableName;
}
