package top.zsmile.system.boot.modules.open.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Schema(description = "文章-上下页")
public class BlogArticleLNBodyVo {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "文章标题", hidden = false)
    private String articleTitle;
}
