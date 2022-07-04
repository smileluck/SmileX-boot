package top.zsmile.modules.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.dao.BaseMapper;
import top.zsmile.modules.blog.entity.BlogTagEntity;

@Mapper
public interface BlogTagMapper extends BaseMapper<BlogTagEntity> {
}