package top.zsmile.system.modules.blog.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 租户博客草稿箱
 */
@TableName("blog_article_draft")
@ApiModel(value = "租户博客草稿箱", description = "租户博客草稿箱")
public class BlogArticleDraftEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID", hidden = false)
    private Long tenantId;
    /**
     * 文章ID
     */
    @ApiModelProperty(value = "文章ID", hidden = false)
    private Long articleId;
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
     * 文章简介
     */
    @ApiModelProperty(value = "文章简介", hidden = false)
    private String articleDigest;
    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容", hidden = false)
    private String articleContent;
    /**
     * 发布状态，0未发布，1已发布
     */
    @ApiModelProperty(value = "发布状态，0未发布，1已发布", hidden = false)
    private Integer publishFlag;

    /**
     * ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 租户ID
     */
    public Long getTenantId() {
        return this.tenantId;
    }

    /**
     * 租户ID
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 文章ID
     */
    public Long getArticleId() {
        return this.articleId;
    }

    /**
     * 文章ID
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     * 文章标题
     */
    public String getArticleTitle() {
        return this.articleTitle;
    }

    /**
     * 文章标题
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * 文章内容
     */
    public String getArticleContent() {
        return this.articleContent;
    }

    /**
     * 文章内容
     */
    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    /**
     * 发布状态，0未发布，1已发布
     */
    public Integer getPublishFlag() {
        return this.publishFlag;
    }

    /**
     * 发布状态，0未发布，1已发布
     */
    public void setPublishFlag(Integer publishFlag) {
        this.publishFlag = publishFlag;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getArticleDigest() {
        return articleDigest;
    }

    public void setArticleDigest(String articleDigest) {
        this.articleDigest = articleDigest;
    }
}
