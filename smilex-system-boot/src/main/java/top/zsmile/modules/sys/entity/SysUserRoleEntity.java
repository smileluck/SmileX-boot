package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

import top.zsmile.annotation.TableName;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统用户关联角色
 */
@TableName("sys_user_role")
@ApiModel(value="系统用户关联角色", description = "系统用户关联角色")
public class SysUserRoleEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
    * 角色id
    */
    @ApiModelProperty(value = "角色id", hidden=false)
    private Long userId;
    /**
    * 菜单id
    */
    @ApiModelProperty(value = "菜单id", hidden=false)
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
