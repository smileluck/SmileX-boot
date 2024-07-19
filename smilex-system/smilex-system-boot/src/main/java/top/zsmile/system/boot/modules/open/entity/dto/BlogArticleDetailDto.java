package top.zsmile.system.boot.modules.open.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Schema(description = "博客文章查询")
public class BlogArticleDetailDto {

    @Schema(description = "租户Id", hidden = true)
    private Long tenantId;

    @NotNull(message = "请选择文章")
    @Schema(description = "文章ID", required = true)
    private Long articleId;

    @Schema(description = "密码TOKEN")
    private String passToken;
}
