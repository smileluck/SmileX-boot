package top.zsmile.modules.generator.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

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
    @NotEmpty(message = "请选择表")
    private List<String> tableName;
}
