package top.zsmile.system.boot.modules.open.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "博客文章列表")
public class BlogArticleVo {
    /**
     * ID
     */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
     * 栏目ID
     */
    @Schema(description = "栏目ID", hidden = false)
    private Long sectionId;
    /**
     * 栏目名称
     */
    @Schema(description = "栏目名称", hidden = false)
    private String sectionName;
    /**
     * 标签id，以,分割
     */
    @Schema(description = "标签id，以,分割", hidden = false)
    private String tagIds;
    /**
     * 标签名称，以,分割
     */
    @Schema(description = "标签名称，以,分割", hidden = false)
    private String tagNames;
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
     * 文章简介
     */
    @Schema(description = "文章简介", hidden = false)
    private String articleDigest;
    /**
     * 文章内容
     */
    @Schema(description = "文章内容", hidden = false)
    private String articleContent;
    /**
     * 访问类型,1通用类型，2统一密码，3独立密码
     */
    @Schema(description = "访问类型,1通用类型，2统一密码，3独立密码", hidden = false)
    private Integer visitType;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", hidden = true)
    private Date createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", hidden = true)
    private Date updateTime;
}
