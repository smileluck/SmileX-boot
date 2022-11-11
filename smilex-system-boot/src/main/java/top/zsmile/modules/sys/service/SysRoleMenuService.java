package top.zsmile.modules.sys.service;

import top.zsmile.modules.sys.entity.dto.SysRoleMenuSaveDto;
import top.zsmile.mybatis.service.BaseService;
import top.zsmile.modules.sys.entity.SysRoleMenuEntity;

public interface SysRoleMenuService extends BaseService<SysRoleMenuEntity> {

    /**
     * 保存角色菜单
     * @param sysRoleMenuSaveDto
     */
    boolean saveRoleMenus(SysRoleMenuSaveDto sysRoleMenuSaveDto);
}