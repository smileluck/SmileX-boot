package top.zsmile.modules.blog.service;

import top.zsmile.service.BaseService;
import top.zsmile.modules.blog.entity.BlogArticleEntity;

public interface BlogArticleService extends BaseService<BlogArticleEntity> {
    boolean saveArticle(BlogArticleEntity blogArticleEntity);
}