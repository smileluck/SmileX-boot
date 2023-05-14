package top.zsmile.system.modules.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.system.modules.blog.entity.BlogCommentEntity;

@Mapper
public interface BlogCommentMapper extends BaseMapper<BlogCommentEntity> {
}