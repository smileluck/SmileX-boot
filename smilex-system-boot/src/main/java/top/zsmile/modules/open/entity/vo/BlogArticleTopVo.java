package top.zsmile.modules.open.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "博客文章排行榜", description = "博客文章排行榜")
public class BlogArticleTopVo {
    /**
     * ID
     */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
     * 文章封面
     */
    @ApiModelProperty(value = "文章封面", hidden = false)
    private String poster;
    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题", hidden = false)
    private String articleTitle;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间", hidden = false)
    private Date createTime;
}
