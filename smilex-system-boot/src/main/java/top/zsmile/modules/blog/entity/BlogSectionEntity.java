package top.zsmile.modules.blog.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;

public class BlogSectionEntity extends BaseEntity implements Serializable {
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
    * 栏目名称
    */
    private String sectionName;
    /**
    * 访问类型，1无限制，2统一密码访问
    */
    private Integer visitType;
    /**
    * 排序
    */
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
