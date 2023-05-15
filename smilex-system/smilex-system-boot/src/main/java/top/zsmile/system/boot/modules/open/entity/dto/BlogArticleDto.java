package top.zsmile.system.boot.modules.open.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "博客文章查询", description = "博客文章查询")
public class BlogArticleDto {

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;
    /**
     * 栏目ID
     */
    @ApiModelProperty(value = "栏目ID")
    private Long sectionId;
    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Long tagId;
    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题")
    private String articleTitle;
    /**
     * 发布状态，0未发布，1已发布
     */
    @ApiModelProperty(value = "发布状态，0未发布，1已发布", hidden = false)
    private Integer publishFlag;
}
