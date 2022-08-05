package top.zsmile.modules.sys.service;

import top.zsmile.service.BaseService;
import top.zsmile.modules.sys.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysMenuService extends BaseService<SysMenuEntity> {
    List<Map<String, Object>> queryMenusByUser();

    Set<String> queryPermsByUser();
}