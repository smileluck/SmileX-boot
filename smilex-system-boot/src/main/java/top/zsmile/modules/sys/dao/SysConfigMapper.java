package top.zsmile.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.modules.sys.entity.SysConfigEntity;

@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfigEntity> {
}