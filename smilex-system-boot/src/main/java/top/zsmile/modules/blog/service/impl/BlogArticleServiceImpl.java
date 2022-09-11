package top.zsmile.modules.blog.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.zsmile.common.utils.PasswordUtils;
import top.zsmile.meta.IPage;
import top.zsmile.meta.Page;
import top.zsmile.modules.open.entity.dto.BlogArticleCommonDto;
import top.zsmile.modules.open.entity.dto.BlogArticleDetailDto;
import top.zsmile.modules.open.entity.dto.BlogArticleDto;
import top.zsmile.modules.open.entity.vo.BlogArticleLNVo;
import top.zsmile.modules.open.entity.vo.BlogArticleTopVo;
import top.zsmile.modules.open.entity.vo.BlogArticleVo;
import top.zsmile.modules.sys.entity.SysTenantEntity;
import top.zsmile.modules.sys.service.SysTenantService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.blog.entity.BlogArticleEntity;
import top.zsmile.modules.blog.dao.BlogArticleMapper;
import top.zsmile.modules.blog.service.BlogArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("blogArticleService")
public class BlogArticleServiceImpl extends BaseServiceImpl<BlogArticleMapper, BlogArticleEntity> implements BlogArticleService {

    @Autowired
    private SysTenantService tenantService;

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

    @Override
    public BlogArticleVo getDetailById(BlogArticleDetailDto blogArticleDto) {
        return getBaseMapper().selectDetailById(blogArticleDto);
    }

    @Override
    public void checkPassToken(Long tenantId, String passToken, BlogArticleVo detail) {
        if (detail.getVisitType() != 1) {
            if (passToken == null) {
                detail.setArticleContent(null);
            } else {
                if (detail.getVisitType() == 2) {
                    SysTenantEntity tenantEntity = tenantService.getById(tenantId, "password", "salt");
                    String pws = PasswordUtils.sha256Hash(passToken + tenantEntity.getSalt());
                    if (!pws.equals(tenantEntity.getPassword())) {
                        detail.setArticleContent(null);
                    }
                } else {
                    BlogArticleEntity articleEntity = getById(detail.getId(), "password", "salt");
                    String pws = PasswordUtils.sha256Hash(passToken + articleEntity.getSalt());
                    if (!pws.equals(articleEntity.getPassword())) {
                        detail.setArticleContent(null);
                    }
                }
            }
        }
    }

    @Override
    public BlogArticleLNVo getLnArticle(BlogArticleCommonDto blogArticleCommonDto) {
        BlogArticleLNVo blogArticleLNVo = new BlogArticleLNVo();
        blogArticleLNVo.setNext(getBaseMapper().selectNextArticle(blogArticleCommonDto));
        blogArticleLNVo.setPrev(getBaseMapper().selectPrevArticle(blogArticleCommonDto));
        return blogArticleLNVo;
    }

    @Override
    public List<BlogArticleTopVo> getTopList(BlogArticleDto blogArticleDto) {
        return getBaseMapper().selectTopList(blogArticleDto);
    }
}
