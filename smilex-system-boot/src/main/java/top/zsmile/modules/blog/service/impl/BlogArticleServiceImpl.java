package top.zsmile.modules.blog.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.transaction.annotation.Transactional;
import top.zsmile.common.utils.PasswordUtils;
import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.blog.entity.BlogArticleEntity;
import top.zsmile.modules.blog.dao.BlogArticleMapper;
import top.zsmile.modules.blog.service.BlogArticleService;
import org.springframework.stereotype.Service;

@Service("blogArticleService")
public class BlogArticleServiceImpl extends BaseServiceImpl<BlogArticleMapper, BlogArticleEntity> implements BlogArticleService {

    @Override
    @Transactional
    public boolean saveArticle(BlogArticleEntity blogArticleEntity) {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        blogArticleEntity.setSalt(salt);
        blogArticleEntity.setPassword(PasswordUtils.sha256Hash(blogArticleEntity.getPassword() + blogArticleEntity.getSalt()));
        return save(blogArticleEntity);
    }
}