package top.zsmile.modules.blog.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.blog.entity.BlogArticleEntity;
import top.zsmile.modules.blog.dao.BlogArticleMapper;
import top.zsmile.modules.blog.service.BlogArticleService;
import org.springframework.stereotype.Service;

@Service("blogArticleService")
public class BlogArticleServiceImpl extends BaseServiceImpl<BlogArticleMapper,BlogArticleEntity> implements BlogArticleService {
}