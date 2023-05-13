package top.zsmile.modules.sys.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("角色授权对象")
public class SysRoleMenuSaveDto {
    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty(value = "角色ID", required = true)
    private Long roleId;

    @NotEmpty(message = "选中菜单不能为空")
    @ApiModelProperty(value = "选中菜单ID列表", required = true)
    private Long[] menuIds;

    @ApiModelProperty(value = "半选中菜单ID列表", required = true)
    private Long[] halfMenuIds;
}
