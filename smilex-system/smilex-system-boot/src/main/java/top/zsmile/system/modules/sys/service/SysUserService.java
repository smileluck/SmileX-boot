package top.zsmile.system.modules.sys.service;

import top.zsmile.system.modules.sys.entity.SysUserEntity;
import top.zsmile.system.modules.sys.model.SysLoginModel;
import top.zsmile.common.mybatis.service.BaseService;

public interface SysUserService extends BaseService<SysUserEntity> {
    /**
     * 保存用户
     */
    boolean saveUser(SysUserEntity sysUserEntity);

    /**
     * 用户登录
     */
    String login(SysLoginModel sysLoginModel);
}