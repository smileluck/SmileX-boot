package top.zsmile.modules.open.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@ApiModel(value = "博客文章查询", description = "博客文章查询")
public class BlogArticleDetailDto {

    @ApiModelProperty(value = "租户Id", hidden = true)
    private Long tenantId;

    @NotNull(message = "请选择文章")
    @ApiModelProperty(value = "文章ID", required = true)
    private Long articleId;

    @ApiModelProperty(value = "密码TOKEN")
    private String passToken;
}
