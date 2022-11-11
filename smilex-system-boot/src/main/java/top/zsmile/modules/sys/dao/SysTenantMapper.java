package top.zsmile.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.mybatis.dao.BaseMapper;
import top.zsmile.modules.sys.entity.SysTenantEntity;

@Mapper
public interface SysTenantMapper extends BaseMapper<SysTenantEntity> {
}