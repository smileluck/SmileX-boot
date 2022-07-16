package top.zsmile.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zsmile.api.common.CommonApi;
import top.zsmile.modules.sys.service.SysMenuService;
import top.zsmile.modules.sys.service.SysUserService;

import java.util.List;
import java.util.Map;

@Service("sysBaseApiImpl")
public class SysBaseApiImpl implements CommonApi {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public Map<String, Object> queryUserById(Long userId, String... columns) {
        return sysUserService.getMapById(userId, columns);
    }

    @Override
    public List<Object> queryUserPerms(Long userId) {
        //TODO 查询实际授权的
        return sysMenuService.queryPermsByUser();
    }
}
