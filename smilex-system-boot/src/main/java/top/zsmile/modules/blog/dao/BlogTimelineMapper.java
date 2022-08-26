package top.zsmile.modules.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.dao.BaseMapper;
import top.zsmile.modules.blog.entity.BlogTimelineEntity;

@Mapper
public interface BlogTimelineMapper extends BaseMapper<BlogTimelineEntity> {
}