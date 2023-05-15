package top.zsmile.system.boot.modules.open.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "博客 - 栏目", description = "博客 - 栏目")
public class BlogSectionVo {
    /**
     * ID
     */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
     * 父ID,最上级为0
     */
    @ApiModelProperty(value = "父ID,最上级为0", hidden = false)
    private Long parentId;
    /**
     * 栏目名称
     */
    @ApiModelProperty(value = "栏目名称", hidden = false)
    private String sectionName;
    /**
     * 访问类型，1无限制，2统一密码访问
     */
    @ApiModelProperty(value = "访问类型，1无限制，2统一密码访问", hidden = false)
    private Integer visitType;
}
