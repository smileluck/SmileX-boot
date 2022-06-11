package top.zsmile.modules.sys.service.impl;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zsmile.modules.sys.dao.SysUserMapper;
import top.zsmile.modules.sys.entity.SysUserEntity;
import top.zsmile.modules.sys.service.SysUserService;
import top.zsmile.service.impl.BaseServiceImpl;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {
    @Override
    @Transactional
    public boolean saveUser(SysUserEntity sysUserEntity) {
//        sysUserEntity.setSalt();
        sysUserEntity.setPassword(new Sha256Hash(sysUserEntity.getPassword(), sysUserEntity.getSalt()).toHex());
        return false;
    }
}