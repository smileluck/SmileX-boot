package top.zsmile.modules.sys.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 角色管理
 */
@TableName("sys_role")
@Schema(description = "角色管理")
public class SysRoleEntity extends BaseEntity implements Serializable {
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
    * 角色名称
    */
    @Schema(description = "角色名称", hidden=false)
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
