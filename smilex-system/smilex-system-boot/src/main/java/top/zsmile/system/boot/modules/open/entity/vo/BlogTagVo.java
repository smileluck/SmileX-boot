package top.zsmile.system.boot.modules.open.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Schema(description = "博客 - 标签")
public class BlogTagVo {
    /**
     * ID
     */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
     * 标签名
     */
    @Schema(description = "标签名")
    private String tagName;
}
