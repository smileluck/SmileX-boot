package top.zsmile.system.boot.modules.open.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "博客文章排行榜")
public class BlogArticleTopVo {
    /**
     * ID
     */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
     * 文章封面
     */
    @Schema(description = "文章封面", hidden = false)
    private String poster;
    /**
     * 文章标题
     */
    @Schema(description = "文章标题", hidden = false)
    private String articleTitle;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "创建时间", hidden = false)
    private Date createTime;
}
