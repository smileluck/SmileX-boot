package top.zsmile.system.boot.modules.blog.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 租户博客栏目
 */
@TableName("blog_section")
@Schema(description = "租户博客栏目")
public class BlogSectionEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
     * 父ID,最上级为0
     */
    @Schema(description = "父ID,最上级为0", hidden = false)
    private Long parentId;
    /**
     * 租户ID
     */
    @Schema(description = "租户ID", hidden = false)
    private Long tenantId;
    /**
     * 层级
     */
    @Schema(description = "层级", hidden = false)
    private Integer level;
    /**
     * 栏目名称
     */
    @Schema(description = "栏目名称", hidden = false)
    private String sectionName;
    /**
     * 访问类型，1无限制，2统一密码访问
     */
    @Schema(description = "访问类型，1无限制，2统一密码访问", hidden = false)
    private Integer visitType;
    /**
     * 栏目类型，1板块，2分组，3路由
     */
    @Schema(description = "栏目类型，1板块，2分组，3路由", hidden = false)
    private Integer type;
    /**
     * 路由URL
     */
    @Schema(description = "路由URL", hidden = false)
    private String routeUrl;
    /**
     * 排序
     */
    @Schema(description = "排序", hidden = false)
    private Integer orderNum;

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
     * 父ID,最上级为0
     */
    public Long getParentId() {
        return this.parentId;
    }

    /**
     * 父ID,最上级为0
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
     * 栏目名称
     */
    public String getSectionName() {
        return this.sectionName;
    }

    /**
     * 栏目名称
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * 访问类型，1无限制，2统一密码访问
     */
    public Integer getVisitType() {
        return this.visitType;
    }

    /**
     * 访问类型，1无限制，2统一密码访问
     */
    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    /**
     * 排序
     */
    public Integer getOrderNum() {
        return this.orderNum;
    }

    /**
     * 排序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 层级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 层级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 栏目类型，1板块，2分组，3路由
     */
    public Integer getType() {
        return type;
    }

    /**
     * 栏目类型，1板块，2分组，3路由
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 路由地址
     */
    public String getRouteUrl() {
        return routeUrl;
    }

    /**
     * 路由地址
     */
    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }
}
