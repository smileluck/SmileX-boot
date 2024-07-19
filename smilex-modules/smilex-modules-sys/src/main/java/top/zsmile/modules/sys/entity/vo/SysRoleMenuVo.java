package top.zsmile.modules.sys.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import top.zsmile.modules.sys.entity.SysMenuEntity;

import java.util.List;

@Data
@Schema(description = "角色权限和所有权限")
@AllArgsConstructor
public class SysRoleMenuVo {

    @Schema(description = "所有权限")
    private List<SysMenuEntity> menus;

    @Schema(description = "当前角色拥有的权限")
    private List<Object> roleMenus;

}
