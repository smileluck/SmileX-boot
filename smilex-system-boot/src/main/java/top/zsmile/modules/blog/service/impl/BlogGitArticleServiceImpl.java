package top.zsmile.modules.blog.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.modules.blog.entity.BlogGitArticleEntity;
import top.zsmile.modules.blog.dao.BlogGitArticleMapper;
import top.zsmile.modules.blog.service.BlogGitArticleService;
import org.springframework.stereotype.Service;

@Service("blogGitArticleService")
public class BlogGitArticleServiceImpl extends BaseServiceImpl<BlogGitArticleMapper,BlogGitArticleEntity> implements BlogGitArticleService {
}