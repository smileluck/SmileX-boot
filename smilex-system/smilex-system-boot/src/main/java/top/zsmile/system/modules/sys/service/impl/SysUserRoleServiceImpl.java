package top.zsmile.system.modules.sys.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.sys.entity.SysUserRoleEntity;
import top.zsmile.system.modules.sys.dao.SysUserRoleMapper;
import top.zsmile.system.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper,SysUserRoleEntity> implements SysUserRoleService {
}