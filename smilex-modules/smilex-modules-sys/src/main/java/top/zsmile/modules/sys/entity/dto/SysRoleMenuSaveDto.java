package top.zsmile.modules.sys.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "角色授权对象")
public class SysRoleMenuSaveDto {
    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色ID", required = true)
    private Long roleId;

    @NotEmpty(message = "选中菜单不能为空")
    @Schema(description = "选中菜单ID列表", required = true)
    private Long[] menuIds;

    @Schema(description = "半选中菜单ID列表", required = true)
    private Long[] halfMenuIds;
}
