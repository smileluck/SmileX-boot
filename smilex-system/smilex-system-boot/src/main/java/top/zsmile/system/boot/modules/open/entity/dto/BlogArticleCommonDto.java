package top.zsmile.system.boot.modules.open.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Schema(description = "博客文章通用查询")
public class BlogArticleCommonDto {

    @Schema(description = "租户Id", hidden = true)
    private Long tenantId;

    @NotNull(message = "请选择文章")
    @Schema(description = "文章ID", required = true)
    private Long articleId;

    @Schema(description = "栏目ID")
    private Long sectionId;

    @Schema(description = "标签ID")
    private Long tagId;

}
