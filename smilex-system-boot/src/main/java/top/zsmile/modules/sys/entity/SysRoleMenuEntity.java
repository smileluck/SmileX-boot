package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;

public class SysRoleMenuEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    private Long id;
    /**
    * 角色id
    */
    private Long roleId;
    /**
    * 菜单id
    */
    private Long menuId;

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
    * 角色id
    */
    public Long getRoleId(){
    return this.roleId;
    }
    /**
    * 角色id
    */
    public void setRoleId(Long roleId){
    this.roleId = roleId;
    }
    /**
    * 菜单id
    */
    public Long getMenuId(){
    return this.menuId;
    }
    /**
    * 菜单id
    */
    public void setMenuId(Long menuId){
    this.menuId = menuId;
    }
}
