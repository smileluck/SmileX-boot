package top.zsmile.system.modules.sys.service.impl;

import top.zsmile.system.modules.sys.entity.dto.SysRoleMenuSaveDto;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.sys.entity.SysRoleMenuEntity;
import top.zsmile.system.modules.sys.dao.SysRoleMenuMapper;
import top.zsmile.system.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuService {
    @Override
    public boolean saveRoleMenus(SysRoleMenuSaveDto sysRoleMenuSaveDto) {
        List<SysRoleMenuEntity> collect = Arrays.stream(sysRoleMenuSaveDto.getMenuIds()).distinct().map(item -> {
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setRoleId(sysRoleMenuSaveDto.getRoleId());
            sysRoleMenuEntity.setMenuId(item);
            sysRoleMenuEntity.setCheckType(1);
            return sysRoleMenuEntity;
        }).collect(Collectors.toList());
        List<SysRoleMenuEntity> collect2 = Arrays.stream(sysRoleMenuSaveDto.getHalfMenuIds()).distinct().map(item -> {
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setRoleId(sysRoleMenuSaveDto.getRoleId());
            sysRoleMenuEntity.setMenuId(item);
            sysRoleMenuEntity.setCheckType(2);
            return sysRoleMenuEntity;
        }).collect(Collectors.toList());
        collect.addAll(collect2);
        getBaseMapper().deleteByMap(Collections.singletonMap("roleId", sysRoleMenuSaveDto.getRoleId()));
        return saveBatch(collect);
    }
}