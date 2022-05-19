package top.zsmile.modules.sys.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysUserEntity;
import top.zsmile.modules.sys.dao.SysUserMapper;
import top.zsmile.modules.sys.service.SysUserService;
import org.springframework.stereotype.Service;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper,SysUserEntity> implements SysUserService {
}