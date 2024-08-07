package top.zsmile.system.boot.modules.blog.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 博客时间线
 */
@TableName("blog_timeline")
@Schema(description = "博客时间线")
public class BlogTimelineEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
     * 租户Id
     */
    @Schema(description = "租户Id", hidden = false)
    private String tenantId;
    /**
     * 年份
     */
    @Schema(description = "年份", hidden = false)
    private String year;
    /**
     * 标题
     */
    @Schema(description = "标题", hidden = false)
    private String title;
    /**
     * 说明
     */
    @Schema(description = "说明", hidden = false)
    private String description;

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
     * 获取：年份
     */
    public String getYear() {
        return this.year;
    }

    /**
     * 设置： 年份
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 获取：标题
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 设置： 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：说明
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置： 说明
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取：租户ID
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 设置： 租户ID
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
