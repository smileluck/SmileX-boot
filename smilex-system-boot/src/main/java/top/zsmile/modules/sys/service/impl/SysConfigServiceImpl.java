package top.zsmile.modules.sys.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysConfigEntity;
import top.zsmile.modules.sys.dao.SysConfigMapper;
import top.zsmile.modules.sys.service.SysConfigService;
import org.springframework.stereotype.Service;

@Service("sysConfigService")
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper,SysConfigEntity> implements SysConfigService {
}