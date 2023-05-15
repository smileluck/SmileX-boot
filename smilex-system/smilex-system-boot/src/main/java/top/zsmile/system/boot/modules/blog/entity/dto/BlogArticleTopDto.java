package top.zsmile.system.boot.modules.blog.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "文章置顶或回退")
public class BlogArticleTopDto {

    @NotNull(message = "请选择文章")
    @ApiModelProperty(value = "文章ID", required = true)
    private Long id;

    /**
     * 发布状态，0未发布，1已发布
     */
    @NotNull(message = "请确认是否置顶或取消")
    @ApiModelProperty(value = "发布状态，0未置顶，1已置顶", required = true)
    private Integer topFlag;
}
