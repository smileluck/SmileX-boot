package top.zsmile.modules.sys.service;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.modules.sys.entity.SysTenantEntity;

public interface SysTenantService extends BaseService<SysTenantEntity> {
    boolean saveTenant(SysTenantEntity sysTenantEntity);


}
