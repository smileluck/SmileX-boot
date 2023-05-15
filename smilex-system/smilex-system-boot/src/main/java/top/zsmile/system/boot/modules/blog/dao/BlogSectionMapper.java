package top.zsmile.system.boot.modules.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.system.boot.modules.blog.entity.BlogSectionEntity;

@Mapper
public interface BlogSectionMapper extends BaseMapper<BlogSectionEntity> {
}