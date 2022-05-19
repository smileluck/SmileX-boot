package top.zsmile.modules.sys.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysTenantEntity;
import top.zsmile.modules.sys.dao.SysTenantMapper;
import top.zsmile.modules.sys.service.SysTenantService;
import org.springframework.stereotype.Service;

@Service("sysTenantService")
public class SysTenantServiceImpl extends BaseServiceImpl<SysTenantMapper,SysTenantEntity> implements SysTenantService {
}