package top.zsmile.modules.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.modules.blog.entity.BlogCommentEntity;

@Mapper
public interface BlogCommentMapper extends BaseMapper<BlogCommentEntity> {
}