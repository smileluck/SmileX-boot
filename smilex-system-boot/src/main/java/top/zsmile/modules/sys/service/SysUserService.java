package top.zsmile.modules.sys.service;

import top.zsmile.modules.sys.entity.SysUserEntity;
import top.zsmile.service.BaseService;

public interface SysUserService extends BaseService<SysUserEntity> {
    /**
     * 保存用户
     */
    boolean saveUser(SysUserEntity sysUserEntity);
}