package top.zsmile.system.boot.modules.open.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Schema(description = "文章-上下页")
public class BlogArticleLNVo {

    @Schema(description = "上一篇")
    private BlogArticleLNBodyVo prev;

    @Schema(description = "下一篇")
    private BlogArticleLNBodyVo next;
}
