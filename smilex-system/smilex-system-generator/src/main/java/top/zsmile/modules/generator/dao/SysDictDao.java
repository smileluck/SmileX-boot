package top.zsmile.modules.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.zsmile.modules.generator.domain.entity.SysDictEntity;

@Mapper
public interface SysDictDao extends BaseMapper<SysDictEntity> {
}
