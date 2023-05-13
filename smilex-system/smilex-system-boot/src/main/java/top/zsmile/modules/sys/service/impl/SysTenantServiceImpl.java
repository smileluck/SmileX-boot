package top.zsmile.modules.sys.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import top.zsmile.common.core.utils.PasswordUtils;
import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysTenantEntity;
import top.zsmile.modules.sys.dao.SysTenantMapper;
import top.zsmile.modules.sys.service.SysTenantService;
import org.springframework.stereotype.Service;

@Service("sysTenantService")
public class SysTenantServiceImpl extends BaseServiceImpl<SysTenantMapper, SysTenantEntity> implements SysTenantService {
    @Override
    public boolean saveTenant(SysTenantEntity sysTenantEntity) {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysTenantEntity.setSalt(salt);
        sysTenantEntity.setPassword(PasswordUtils.sha256Hash(sysTenantEntity.getPassword() + sysTenantEntity.getSalt()));
        return save(sysTenantEntity);
    }
}