package top.zsmile.modules.sys.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysRoleMenuEntity;
import top.zsmile.modules.sys.dao.SysRoleMenuMapper;
import top.zsmile.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper,SysRoleMenuEntity> implements SysRoleMenuService {
}