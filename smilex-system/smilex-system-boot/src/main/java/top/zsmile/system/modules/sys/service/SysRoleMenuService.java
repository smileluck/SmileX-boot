package top.zsmile.system.modules.sys.service;

import top.zsmile.system.modules.sys.entity.dto.SysRoleMenuSaveDto;
import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.system.modules.sys.entity.SysRoleMenuEntity;

public interface SysRoleMenuService extends BaseService<SysRoleMenuEntity> {

    /**
     * 保存角色菜单
     * @param sysRoleMenuSaveDto
     */
    boolean saveRoleMenus(SysRoleMenuSaveDto sysRoleMenuSaveDto);
}