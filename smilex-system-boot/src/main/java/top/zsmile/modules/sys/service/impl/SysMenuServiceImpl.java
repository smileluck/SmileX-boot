package top.zsmile.modules.sys.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysMenuEntity;
import top.zsmile.modules.sys.dao.SysMenuMapper;
import top.zsmile.modules.sys.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysMenuService")
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {
    @Override
    public List<Map<String, Object>> queryMenusByUser() {

        List<Map<String, Object>> maps = baseMapper.selectMenusByUser();
        return maps;
    }

    @Override
    public List<Object> queryPermsByUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("menuType", 2);
        map.put("enableFlag", 1);

        List<Object> perm = baseMapper.selectSingleByMap(map, "perm");
        return perm;
    }
}