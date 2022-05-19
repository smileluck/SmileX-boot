package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;

public class SysRoleEntity extends BaseEntity implements Serializable {
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
    * 角色名称
    */
    private String roleName;

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
    * 角色名称
    */
    public String getRoleName(){
    return this.roleName;
    }
    /**
    * 角色名称
    */
    public void setRoleName(String roleName){
    this.roleName = roleName;
    }
}
