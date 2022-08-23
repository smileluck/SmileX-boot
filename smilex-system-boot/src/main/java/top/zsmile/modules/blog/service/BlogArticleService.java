package top.zsmile.modules.blog.service;

import org.apache.ibatis.annotations.Param;
import top.zsmile.meta.IPage;
import top.zsmile.meta.Page;
import top.zsmile.modules.open.entity.dto.BlogArticleDto;
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
}