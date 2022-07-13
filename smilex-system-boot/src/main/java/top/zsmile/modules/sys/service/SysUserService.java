package top.zsmile.modules.sys.service;

import top.zsmile.modules.sys.entity.SysUserEntity;
import top.zsmile.modules.sys.model.SysLoginModel;
import top.zsmile.service.BaseService;

public interface SysUserService extends BaseService<SysUserEntity> {
    /**
     * �����û�
     */
    boolean saveUser(SysUserEntity sysUserEntity);

    /**
     * �û���¼
     */
    String login(SysLoginModel sysLoginModel);
}