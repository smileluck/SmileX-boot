package top.zsmile.tool.gen.domain.entity;

import lombok.Data;
import top.zsmile.common.web.validator.group.Add;

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
     * 保存位置
     */
    @NotBlank(message = "保存位置不能为空", groups = Add.class)
    private String savePath;
    /**
     * 表名
     */
    @NotEmpty(message = "请选择表")
    private List<String> tableName;
}
