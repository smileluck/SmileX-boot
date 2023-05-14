package top.zsmile.system.modules.sys.service;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.system.modules.sys.entity.SysTenantEntity;

public interface SysTenantService extends BaseService<SysTenantEntity> {
    boolean saveTenant(SysTenantEntity sysTenantEntity);


}
