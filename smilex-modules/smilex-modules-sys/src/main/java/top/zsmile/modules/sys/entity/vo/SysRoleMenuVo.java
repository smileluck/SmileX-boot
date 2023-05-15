package top.zsmile.modules.sys.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import top.zsmile.modules.sys.entity.SysMenuEntity;

import java.util.List;

@Data
@ApiModel("角色权限和所有权限")
@AllArgsConstructor
public class SysRoleMenuVo {

    @ApiModelProperty("所有权限")
    private List<SysMenuEntity> menus;

    @ApiModelProperty("当前角色拥有的权限")
    private List<Object> roleMenus;

}
