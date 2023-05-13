package top.zsmile.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.modules.sys.entity.SysLogEntity;

@Mapper
public interface SysLogMapper extends BaseMapper<SysLogEntity> {
}