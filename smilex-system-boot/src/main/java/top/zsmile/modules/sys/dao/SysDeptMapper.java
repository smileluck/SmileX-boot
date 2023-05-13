package top.zsmile.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.modules.sys.entity.SysDeptEntity;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDeptEntity> {
}