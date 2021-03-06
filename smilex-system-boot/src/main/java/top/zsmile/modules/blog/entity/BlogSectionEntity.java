package top.zsmile.modules.blog.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 租户博客栏目
 */
@ApiModel(value="租户博客栏目", description = "租户博客栏目")
public class BlogSectionEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
    * 父ID,最上级为0
    */
    @ApiModelProperty(value = "父ID,最上级为0", hidden=false)
    private Long parentId;
    /**
    * 租户ID
    */
    @ApiModelProperty(value = "租户ID", hidden=false)
    private Long tenantId;
    /**
    * 栏目名称
    */
    @ApiModelProperty(value = "栏目名称", hidden=false)
    private String sectionName;
    /**
    * 访问类型，1无限制，2统一密码访问
    */
    @ApiModelProperty(value = "访问类型，1无限制，2统一密码访问", hidden=false)
    private Integer visitType;
    /**
    * 排序
    */
    @ApiModelProperty(value = "排序", hidden=false)
    private Integer orderNum;

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
    * 父ID,最上级为0
    */
    public Long getParentId(){
        return this.parentId;
    }
    /**
    * 父ID,最上级为0
    */
    public void setParentId(Long parentId){
        this.parentId = parentId;
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
    * 栏目名称
    */
    public String getSectionName(){
        return this.sectionName;
    }
    /**
    * 栏目名称
    */
    public void setSectionName(String sectionName){
        this.sectionName = sectionName;
    }
    /**
    * 访问类型，1无限制，2统一密码访问
    */
    public Integer getVisitType(){
        return this.visitType;
    }
    /**
    * 访问类型，1无限制，2统一密码访问
    */
    public void setVisitType(Integer visitType){
        this.visitType = visitType;
    }
    /**
    * 排序
    */
    public Integer getOrderNum(){
        return this.orderNum;
    }
    /**
    * 排序
    */
    public void setOrderNum(Integer orderNum){
        this.orderNum = orderNum;
    }
}
