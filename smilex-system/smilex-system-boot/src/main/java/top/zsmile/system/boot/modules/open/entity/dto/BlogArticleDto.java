package top.zsmile.system.boot.modules.open.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Schema(description = "博客文章查询")
public class BlogArticleDto {

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;
    /**
     * 栏目ID
     */
    @Schema(description = "栏目ID")
    private Long sectionId;
    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Long tagId;
    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String articleTitle;
    /**
     * 发布状态，0未发布，1已发布
     */
    @Schema(description = "发布状态，0未发布，1已发布", hidden = false)
    private Integer publishFlag;
}
