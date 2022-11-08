package top.zsmile.modules.blog.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import top.zsmile.annotation.TableName;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 租户博客-git文章同步
 */
@TableName("blog_git_article")
@ApiModel(value = "租户博客-git文章同步", description = "租户博客-git文章同步")
public class BlogGitArticleEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
     * 博客文章ID
     */
    @ApiModelProperty(value = "博客文章ID", hidden = false)
    private Long blogArticleId;
    /**
     * 文件标题
     */
    @ApiModelProperty(value = "文件标题", hidden = false)
    private String fileTitle;
    /**
     * 内容URL
     */
    @ApiModelProperty(value = "内容URL", hidden = false)
    private String contentUrl;
    /**
     * 内容URL
     */
    @ApiModelProperty(value = "内容Text", hidden = false)
    private String contentText;
    /**
     * 是否更新，1是，0否
     */
    @ApiModelProperty(value = "是否更新，1是，0否", hidden = false)
    private Integer updateFlag;

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
}
