package top.zsmile.system.boot.modules.blog.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 租户博客标签
 */
@TableName("blog_tag")
@Schema(description = "租户博客标签")
public class BlogTagEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
    * 租户ID
    */
    @Schema(description = "租户ID", hidden=false)
    private Long tenantId;
    /**
    * 标签名
    */
    @Schema(description = "标签名", hidden=false)
    private String tagName;
    /**
    * 是否启用，0禁用1启用
    */
    @Schema(description = "是否启用，0禁用1启用", hidden=false)
    private Integer enableFlag;

    /**
    * ID
    */
    public Long getId(){
        return this.id;
    }
    /**
    * ID
    */
    public void setId(Long id){
        this.id = id;
    }
    /**
    * 租户ID
    */
    public Long getTenantId(){
        return this.tenantId;
    }
    /**
    * 租户ID
    */
    public void setTenantId(Long tenantId){
        this.tenantId = tenantId;
    }
    /**
    * 标签名
    */
    public String getTagName(){
        return this.tagName;
    }
    /**
    * 标签名
    */
    public void setTagName(String tagName){
        this.tagName = tagName;
    }
    /**
    * 是否启用，0禁用1启用
    */
    public Integer getEnableFlag(){
        return this.enableFlag;
    }
    /**
    * 是否启用，0禁用1启用
    */
    public void setEnableFlag(Integer enableFlag){
        this.enableFlag = enableFlag;
    }
}
