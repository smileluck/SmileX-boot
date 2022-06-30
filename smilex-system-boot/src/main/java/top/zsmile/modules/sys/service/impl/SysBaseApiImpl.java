package top.zsmile.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zsmile.core.api.CommonApi;
import top.zsmile.modules.sys.service.SysUserService;

import java.util.Map;

@Service("sysBaseApiImpl")
public class SysBaseApiImpl implements CommonApi {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Map<String, Object> queryUserById(Long userId, String... columns) {
        return sysUserService.getMapById(userId, columns);
    }
}
