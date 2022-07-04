package top.zsmile.modules.blog.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;

public class BlogTagEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    private Long id;
    /**
    * 租户ID
    */
    private Long tenantId;
    /**
    * 标签名
    */
    private String tagName;
    /**
    * 是否启用，0禁用1启用
    */
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
