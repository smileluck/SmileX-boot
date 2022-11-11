package top.zsmile.modules.sys.service.impl;

import top.zsmile.mybatis.service.BaseService;
import top.zsmile.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysUserRoleEntity;
import top.zsmile.modules.sys.dao.SysUserRoleMapper;
import top.zsmile.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper,SysUserRoleEntity> implements SysUserRoleService {
}