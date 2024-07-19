package top.zsmile.system.boot.modules.blog.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 租户博客-git文章同步
 */
@TableName("blog_git_article")
@Schema(description = "租户博客-git文章同步")
public class BlogGitArticleEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
     * 博客文章ID
     */
    @Schema(description = "博客文章ID", hidden = false)
    private Long blogArticleId;
    /**
     * 文件标题
     */
    @Schema(description = "文件标题", hidden = false)
    private String fileTitle;
    /**
     * 内容URL
     */
    @Schema(description = "内容URL", hidden = false)
    private String contentUrl;
    /**
     * 内容URL
     */
    @Schema(description = "内容Text", hidden = false)
    private String contentText;
    /**
     * 是否更新，1是，0否
     */
    @Schema(description = "是否更新，1是，0否", hidden = false)
    private Integer updateFlag;
    /**
     * 是否发布，0未发布，1已发布
     */
    @Schema(description = "是否发布，0未发布，1已发布", hidden = false)
    private Integer publishFlag;
    /**
     * 同步时间
     */
    @Schema(description = "同步时间", hidden = false)
    private LocalDateTime asyncTime;
    /**
     * 发布时间
     */
    @Schema(description = "发布时间", hidden = false)
    private LocalDateTime publishTime;

    /**
     * 获取：ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置：ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：博客文章ID
     */
    public Long getBlogArticleId() {
        return this.blogArticleId;
    }

    /**
     * 设置： 博客文章ID
     */
    public void setBlogArticleId(Long blogArticleId) {
        this.blogArticleId = blogArticleId;
    }

    /**
     * 获取：内容URL
     */
    public String getContentUrl() {
        return this.contentUrl;
    }

    /**
     * 设置： 内容URL
     */
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    /**
     * 获取：是否更新，1是，0否
     */
    public Integer getUpdateFlag() {
        return this.updateFlag;
    }

    /**
     * 设置： 是否更新，1是，0否
     */
    public void setUpdateFlag(Integer updateFlag) {
        this.updateFlag = updateFlag;
    }

    /**
     * 获取： 内容Text
     */
    public String getContentText() {
        return contentText;
    }

    /**
     * 设置： 内容Text
     */
    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    /**
     * 设置： 文件标题
     */
    public String getFileTitle() {
        return fileTitle;
    }

    /**
     * 设置： 文件标题
     */
    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    /**
     * 设置： 同步时间
     */
    public LocalDateTime getAsyncTime() {
        return asyncTime;
    }

    /**
     * 设置： 同步时间
     */
    public void setAsyncTime(LocalDateTime asyncTime) {
        this.asyncTime = asyncTime;
    }

    /**
     * 设置： 发布时间
     */
    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    /**
     * 设置： 发布时间
     */
    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getPublishFlag() {
        return publishFlag;
    }

    public void setPublishFlag(Integer publishFlag) {
        this.publishFlag = publishFlag;
    }
}
