package top.zsmile.system.boot.modules.blog.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 租户博客文章
 */
@TableName("blog_article")
@ApiModel(value = "租户博客文章", description = "租户博客文章")
public class BlogArticleEntity extends BaseEntity implements Serializable {
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
     * 栏目ID
     */
    @ApiModelProperty(value = "栏目ID", hidden = false)
    private Long sectionId;
    /**
     * 标签id，以,分割
     */
    @ApiModelProperty(value = "标签id，以,分割", hidden = false)
    private String tagIds;
    /**
     * 标签名称，以,分割
     */
    @ApiModelProperty(value = "标签名称，以,分割", hidden = false)
    private String tagNames;
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
     * 语法类型，1markdown，2html
     */
    @ApiModelProperty(value = "语法类型，1markdown，2html", hidden = false)
    private Integer grammarType;
    /**
     * 访问类型,1通用类型，2统一密码，3独立密码
     */
    @ApiModelProperty(value = "访问类型,1通用类型，2统一密码，3独立密码", hidden = false)
    private Integer visitType;
    /**
     * 独立密码
     */
    @ApiModelProperty(value = "独立密码", hidden = true)
    private String password;
    /**
     * salt
     */
    @ApiModelProperty(value = "salt", hidden = true)
    private String salt;
    /**
     * 发布状态，0未发布，1已发布
     */
    @ApiModelProperty(value = "发布状态，0未发布，1已发布", hidden = false)
    private Integer publishFlag;

    /**
     * 置顶状态，0未置顶，1已置顶
     */
    @ApiModelProperty(value = "置顶状态，0未置顶，1已置顶", hidden = false)
    private Integer topFlag;

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
     * 栏目ID
     */
    public Long getSectionId() {
        return this.sectionId;
    }

    /**
     * 栏目ID
     */
    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * 标签id，以,分割
     */
    public String getTagIds() {
        return this.tagIds;
    }

    /**
     * 标签id，以,分割
     */
    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
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
     * 文章简介
     */
    public String getArticleDigest() {
        return this.articleDigest;
    }

    /**
     * 文章简介
     */
    public void setArticleDigest(String articleDigest) {
        this.articleDigest = articleDigest;
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
     * 语法类型，1markdown，2html
     */
    public Integer getGrammarType() {
        return this.grammarType;
    }

    /**
     * 语法类型，1markdown，2html
     */
    public void setGrammarType(Integer grammarType) {
        this.grammarType = grammarType;
    }

    /**
     * 访问类型,1通用类型，2统一密码，3独立密码
     */
    public Integer getVisitType() {
        return this.visitType;
    }

    /**
     * 访问类型,1通用类型，2统一密码，3独立密码
     */
    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    /**
     * 独立密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 独立密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * salt
     */
    public String getSalt() {
        return this.salt;
    }

    /**
     * salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
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

    /**
     * 文章封面
     */
    public String getPoster() {
        return poster;
    }

    /**
     * 文章封面
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    /**
     * 标签名称，以,分割
     */
    public String getTagNames() {
        return tagNames;
    }

    /**
     * 标签名称，以,分割
     */
    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    /**
     * 置顶状态，0未置顶，1已置顶
     */
    public Integer getTopFlag() {
        return topFlag;
    }

    /**
     * 置顶状态，0未置顶，1已置顶
     */
    public void setTopFlag(Integer topFlag) {
        this.topFlag = topFlag;
    }
}
