package top.zsmile.system.modules.blog.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.blog.entity.BlogGitArticleEntity;
import top.zsmile.system.modules.blog.dao.BlogGitArticleMapper;
import top.zsmile.system.modules.blog.service.BlogGitArticleService;
import org.springframework.stereotype.Service;

@Service("blogGitArticleService")
public class BlogGitArticleServiceImpl extends BaseServiceImpl<BlogGitArticleMapper,BlogGitArticleEntity> implements BlogGitArticleService {
}