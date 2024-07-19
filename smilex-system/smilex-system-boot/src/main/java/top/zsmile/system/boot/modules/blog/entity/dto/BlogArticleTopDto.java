package top.zsmile.system.boot.modules.blog.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Schema(description = "文章置顶或回退")
public class BlogArticleTopDto {

    @NotNull(message = "请选择文章")
    @Schema(description = "文章ID", required = true)
    private Long id;

    /**
     * 发布状态，0未发布，1已发布
     */
    @NotNull(message = "请确认是否置顶或取消")
    @Schema(description = "发布状态，0未置顶，1已置顶", required = true)
    private Integer topFlag;
}
