package top.zsmile.modules.blog.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.transaction.annotation.Transactional;
import top.zsmile.common.utils.PasswordUtils;
import top.zsmile.meta.IPage;
import top.zsmile.meta.Page;
import top.zsmile.modules.open.entity.dto.BlogArticleDto;
import top.zsmile.modules.open.entity.vo.BlogArticleVo;
import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.blog.entity.BlogArticleEntity;
import top.zsmile.modules.blog.dao.BlogArticleMapper;
import top.zsmile.modules.blog.service.BlogArticleService;
import org.springframework.stereotype.Service;
import top.zsmile.utils.Constants;
import top.zsmile.utils.PageQuery;

import java.util.List;

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

    @Override
    public IPage<BlogArticleVo> getListBySearch(Page page, BlogArticleDto blogArticleDto) {
        List<BlogArticleVo> list = getBaseMapper().selectListBySearch(page, blogArticleDto);
        Integer count = getBaseMapper().selectListCountBySearch(blogArticleDto);
        page.setRecords(list);
        page.setTotal(count);
        return page;
    }
}