package top.zsmile.modules.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.mybatis.dao.BaseMapper;
import top.zsmile.modules.blog.entity.BlogArticleDraftEntity;

@Mapper
public interface BlogArticleDraftMapper extends BaseMapper<BlogArticleDraftEntity> {
}