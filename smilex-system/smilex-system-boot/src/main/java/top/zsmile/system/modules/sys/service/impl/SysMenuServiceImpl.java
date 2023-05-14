package top.zsmile.system.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.zsmile.api.system.common.CommonAuthApi;
import top.zsmile.common.core.constant.CommonConstant;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.sys.entity.SysMenuEntity;
import top.zsmile.system.modules.sys.dao.SysMenuMapper;
import top.zsmile.system.modules.sys.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("sysMenuService")
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    @Autowired
    private CommonAuthApi commonAuthApi;

    @Override
    public List<Map<String, Object>> queryMenusByUser() {
        Long userId = commonAuthApi.queryUserId();
        List<Map<String, Object>> maps;
        if (userId.equals(CommonConstant.SUPER_ADMIN_ID)) {
            maps = getBaseMapper().selectMenus();
        } else {
            maps = getBaseMapper().selectMenusByUser(userId);
        }
        return maps;
    }

    @Override
    public Set<String> queryPermsByUser() {
        Long userId = commonAuthApi.queryUserId();
        List<Object> lists;
        if (userId.equals(CommonConstant.SUPER_ADMIN_ID)) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("menuType", 2);
            map.put("enableFlag", 1);
            lists = getBaseMapper().selectSingleByMap(map, "perm");
        } else {
            lists = getBaseMapper().selectPermsByUser(userId);
        }
        ListIterator<Object> iterator = lists.listIterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            String str = obj.toString();
            if (str.contains(";")) {
                iterator.remove();
                String[] split = str.split(";");
                for (String tempStr : split) {
                    iterator.add(tempStr);
                }
                continue;
            }
        }
        Set<String> sets = lists.stream().map(Object::toString).collect(Collectors.toSet());
        return sets;
    }
}