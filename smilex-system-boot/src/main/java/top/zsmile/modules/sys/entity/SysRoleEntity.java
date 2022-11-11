package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

import top.zsmile.mybatis.annotation.TableName;
import top.zsmile.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色管理
 */
@TableName("sys_role")
@ApiModel(value="角色管理", description = "角色管理")
public class SysRoleEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
    * 租户ID
    */
    @ApiModelProperty(value = "租户ID", hidden=false)
    private Long tenantId;
    /**
    * 角色名称
    */
    @ApiModelProperty(value = "角色名称", hidden=false)
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
