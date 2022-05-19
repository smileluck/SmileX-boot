package top.zsmile.modules.sys.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysMenuEntity;
import top.zsmile.modules.sys.dao.SysMenuMapper;
import top.zsmile.modules.sys.service.SysMenuService;
import org.springframework.stereotype.Service;

@Service("sysMenuService")
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper,SysMenuEntity> implements SysMenuService {
}