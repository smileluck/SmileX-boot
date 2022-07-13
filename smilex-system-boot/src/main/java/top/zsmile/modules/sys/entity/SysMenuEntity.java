package top.zsmile.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import top.zsmile.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统菜单管理
 */
@ApiModel(value="系统菜单管理", description = "系统菜单管理")
public class SysMenuEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
    * 父ID,最上级则为0
    */
    @ApiModelProperty(value = "父ID,最上级则为0", hidden=false)
    private Long parentId;
    /**
    * 菜单名称
    */
    @ApiModelProperty(value = "菜单名称", hidden=false)
    private String menuName;
    /**
    * 菜单icon
    */
    @ApiModelProperty(value = "菜单icon", hidden=false)
    private String menuIcon;
    /**
    * 路由地址
    */
    @ApiModelProperty(value = "路由地址", hidden=false)
    private String routeUrl;
    /**
    * 路由视图
    */
    @ApiModelProperty(value = "路由视图", hidden=false)
    private String routeView;
    /**
    * 菜单类型(0:菜单组; 1:子菜单; 2:按钮权限)
    */
    @ApiModelProperty(value = "菜单类型(0:菜单组; 1:子菜单; 2:按钮权限)", hidden=false)
    private Integer menuType;
    /**
    * 权限标识
    */
    @ApiModelProperty(value = "权限标识", hidden=false)
    private String perm;
    /**
    * 排序
    */
    @ApiModelProperty(value = "排序", hidden=false)
    private Integer orderNum;
    /**
    * 是否启用，0禁用1启用
    */
    @ApiModelProperty(value = "是否启用，0禁用1启用", hidden=false)
    private Integer enableFlag;

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
    * 父ID,最上级则为0
    */
    public Long getParentId(){
        return this.parentId;
    }
    /**
    * 父ID,最上级则为0
    */
    public void setParentId(Long parentId){
        this.parentId = parentId;
    }
    /**
    * 菜单名称
    */
    public String getMenuName(){
        return this.menuName;
    }
    /**
    * 菜单名称
    */
    public void setMenuName(String menuName){
        this.menuName = menuName;
    }
    /**
    * 菜单icon
    */
    public String getMenuIcon(){
        return this.menuIcon;
    }
    /**
    * 菜单icon
    */
    public void setMenuIcon(String menuIcon){
        this.menuIcon = menuIcon;
    }
    /**
    * 路由地址
    */
    public String getRouteUrl(){
        return this.routeUrl;
    }
    /**
    * 路由地址
    */
    public void setRouteUrl(String routeUrl){
        this.routeUrl = routeUrl;
    }
    /**
    * 路由视图
    */
    public String getRouteView(){
        return this.routeView;
    }
    /**
    * 路由视图
    */
    public void setRouteView(String routeView){
        this.routeView = routeView;
    }
    /**
    * 菜单类型(0:菜单组; 1:子菜单; 2:按钮权限)
    */
    public Integer getMenuType(){
        return this.menuType;
    }
    /**
    * 菜单类型(0:菜单组; 1:子菜单; 2:按钮权限)
    */
    public void setMenuType(Integer menuType){
        this.menuType = menuType;
    }
    /**
    * 权限标识
    */
    public String getPerm(){
        return this.perm;
    }
    /**
    * 权限标识
    */
    public void setPerm(String perm){
        this.perm = perm;
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
    /**
    * 是否启用，0禁用1启用
    */
    public Integer getEnableFlag(){
        return this.enableFlag;
    }
    /**
    * 是否启用，0禁用1启用
    */
    public void setEnableFlag(Integer enableFlag){
        this.enableFlag = enableFlag;
    }
}
