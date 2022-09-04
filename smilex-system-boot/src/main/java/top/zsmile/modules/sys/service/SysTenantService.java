package top.zsmile.modules.sys.service;

import top.zsmile.service.BaseService;
import top.zsmile.modules.sys.entity.SysTenantEntity;

public interface SysTenantService extends BaseService<SysTenantEntity> {
    boolean saveTenant(SysTenantEntity sysTenantEntity);


}
