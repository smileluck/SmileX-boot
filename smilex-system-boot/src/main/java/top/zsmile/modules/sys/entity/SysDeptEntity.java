package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;

public class SysDeptEntity extends BaseEntity implements Serializable {
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
    * 父级部门，不存在则为0
    */
    private Long parentId;
    /**
    * 部门名称
    */
    private String deptName;

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
    * 父级部门，不存在则为0
    */
    public Long getParentId(){
    return this.parentId;
    }
    /**
    * 父级部门，不存在则为0
    */
    public void setParentId(Long parentId){
    this.parentId = parentId;
    }
    /**
    * 部门名称
    */
    public String getDeptName(){
    return this.deptName;
    }
    /**
    * 部门名称
    */
    public void setDeptName(String deptName){
    this.deptName = deptName;
    }
}
