package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统角色菜单
 */
@ApiModel(value="系统角色菜单", description = "系统角色菜单")
public class SysRoleMenuEntity implements Serializable {
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
    private Long roleId;
    /**
    * 菜单id
    */
    @ApiModelProperty(value = "菜单id", hidden=false)
    private Long menuId;

    /**
     * 选中状态.1选中，2半选中
     */
    @ApiModelProperty(value = "选中状态1选中，2半选中", hidden=false)
    private Integer menuType;

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

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }
}
