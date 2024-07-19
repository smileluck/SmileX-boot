package top.zsmile.modules.sys.entity;

import java.io.Serializable;

import top.zsmile.common.mybatis.annotation.TableName;
import top.zsmile.common.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统用户关联角色
 */
@TableName("sys_user_role")
@Schema(description = "系统用户关联角色")
public class SysUserRoleEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Schema(description = "Id，更新时需要传")
    private Long id;
    /**
    * 角色id
    */
    @Schema(description = "角色id", hidden=false)
    private Long userId;
    /**
    * 菜单id
    */
    @Schema(description = "菜单id", hidden=false)
    private Long roleId;

    /**
    * 获取：ID
    */
    public Long getId(){
        return this.id;
    }
    /**
    * 设置：ID
    */
    public void setId(Long id){
        this.id = id;
    }
    /**
    * 获取：角色id
    */
    public Long getUserId(){
        return this.userId;
    }
    /**
    * 设置： 角色id
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }
    /**
    * 获取：菜单id
    */
    public Long getRoleId(){
        return this.roleId;
    }
    /**
    * 设置： 菜单id
    */
    public void setRoleId(Long roleId){
        this.roleId = roleId;
    }
}
