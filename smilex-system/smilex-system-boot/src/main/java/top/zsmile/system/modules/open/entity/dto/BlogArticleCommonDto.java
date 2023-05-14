package top.zsmile.system.modules.open.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@ApiModel(value = "博客文章通用查询", description = "博客文章通用查询")
public class BlogArticleCommonDto {

    @ApiModelProperty(value = "租户Id", hidden = true)
    private Long tenantId;

    @NotNull(message = "请选择文章")
    @ApiModelProperty(value = "文章ID", required = true)
    private Long articleId;

    @ApiModelProperty(value = "栏目ID")
    private Long sectionId;

    @ApiModelProperty(value = "标签ID")
    private Long tagId;

}
