package top.zsmile.system.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.system.modules.sys.entity.SysRoleEntity;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {
}