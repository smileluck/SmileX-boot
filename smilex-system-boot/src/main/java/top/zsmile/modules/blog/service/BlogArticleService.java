package top.zsmile.modules.blog.service;

import top.zsmile.meta.IPage;
import top.zsmile.meta.Page;
import top.zsmile.modules.open.entity.dto.BlogArticleCommonDto;
import top.zsmile.modules.open.entity.dto.BlogArticleDetailDto;
import top.zsmile.modules.open.entity.dto.BlogArticleDto;
import top.zsmile.modules.open.entity.vo.BlogArticleLNVo;
import top.zsmile.modules.open.entity.vo.BlogArticleTopVo;
import top.zsmile.modules.open.entity.vo.BlogArticleVo;
import top.zsmile.service.BaseService;
import top.zsmile.modules.blog.entity.BlogArticleEntity;

import java.util.List;

public interface BlogArticleService extends BaseService<BlogArticleEntity> {
    /**
     * 保存新文章
     *
     * @param blogArticleEntity
     * @return
     */
    boolean saveArticle(BlogArticleEntity blogArticleEntity);

    /**
     * 查询文章列表
     *
     * @param blogArticleDto
     * @return
     */
    IPage<BlogArticleVo> getListBySearch(Page page, BlogArticleDto blogArticleDto);


    /**
     * 查询文章内容
     *
     * @param blogArticleDto
     * @return
     */
    BlogArticleVo getDetailById(BlogArticleDetailDto blogArticleDto);

    /**
     * 验证文章是否可以访问
     *
     * @param tenantId  租户ID
     * @param passToken 密码
     * @param detail    详情
     */
    void checkPassToken(Long tenantId, String passToken, BlogArticleVo detail);

    /**
     * 获取上下页文章
     *
     * @param blogArticleCommonDto
     * @return
     */
    BlogArticleLNVo getLnArticle(BlogArticleCommonDto blogArticleCommonDto);

    /**
     * 获取排行榜文章列表
     *
     * @param blogArticleDto
     * @return
     */
    List<BlogArticleTopVo> getTopList(BlogArticleDto blogArticleDto);
}
