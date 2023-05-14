package top.zsmile.system.modules.sys.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.sys.entity.SysConfigEntity;
import top.zsmile.system.modules.sys.dao.SysConfigMapper;
import top.zsmile.system.modules.sys.service.SysConfigService;
import org.springframework.stereotype.Service;

@Service("sysConfigService")
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper,SysConfigEntity> implements SysConfigService {
}