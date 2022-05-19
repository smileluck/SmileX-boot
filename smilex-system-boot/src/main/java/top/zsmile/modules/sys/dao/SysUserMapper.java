package top.zsmile.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.dao.BaseMapper;
import top.zsmile.modules.sys.entity.SysUserEntity;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {
}