package top.zsmile.modules.blog.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.modules.blog.entity.BlogCommentEntity;
import top.zsmile.modules.blog.dao.BlogCommentMapper;
import top.zsmile.modules.blog.service.BlogCommentService;
import org.springframework.stereotype.Service;

@Service("blogCommentService")
public class BlogCommentServiceImpl extends BaseServiceImpl<BlogCommentMapper,BlogCommentEntity> implements BlogCommentService {
}