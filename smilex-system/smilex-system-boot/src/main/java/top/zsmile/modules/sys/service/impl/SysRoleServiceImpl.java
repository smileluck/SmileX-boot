package top.zsmile.modules.sys.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysRoleEntity;
import top.zsmile.modules.sys.dao.SysRoleMapper;
import top.zsmile.modules.sys.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper,SysRoleEntity> implements SysRoleService {
}