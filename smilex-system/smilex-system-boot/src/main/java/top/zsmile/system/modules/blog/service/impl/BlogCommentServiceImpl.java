package top.zsmile.system.modules.blog.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.blog.entity.BlogCommentEntity;
import top.zsmile.system.modules.blog.dao.BlogCommentMapper;
import top.zsmile.system.modules.blog.service.BlogCommentService;
import org.springframework.stereotype.Service;

@Service("blogCommentService")
public class BlogCommentServiceImpl extends BaseServiceImpl<BlogCommentMapper,BlogCommentEntity> implements BlogCommentService {
}