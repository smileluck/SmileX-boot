package top.zsmile.modules.sys.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zsmile.common.utils.PasswordUtils;
import top.zsmile.modules.sys.dao.SysUserMapper;
import top.zsmile.modules.sys.entity.SysUserEntity;
import top.zsmile.modules.sys.service.SysUserService;
import top.zsmile.service.impl.BaseServiceImpl;

import java.util.Date;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {
    @Override
    @Transactional
    public boolean saveUser(SysUserEntity sysUserEntity) {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUserEntity.setSalt(salt);
        sysUserEntity.setPassword(PasswordUtils.sha256Hash(sysUserEntity.getPassword() + sysUserEntity.getSalt()));

        Date date = new Date();
        sysUserEntity.setCreateTime(date);
        sysUserEntity.setUpdateTime(date);

        return save(sysUserEntity);
    }
}