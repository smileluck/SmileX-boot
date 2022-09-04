package top.zsmile.modules.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zsmile.dao.BaseMapper;
import top.zsmile.meta.IPage;
import top.zsmile.meta.Page;
import top.zsmile.modules.blog.entity.BlogArticleEntity;
import top.zsmile.modules.open.entity.dto.BlogArticleCommonDto;
import top.zsmile.modules.open.entity.dto.BlogArticleDetailDto;
import top.zsmile.modules.open.entity.dto.BlogArticleDto;
import top.zsmile.modules.open.entity.vo.BlogArticleLNBodyVo;
import top.zsmile.modules.open.entity.vo.BlogArticleLNVo;
import top.zsmile.modules.open.entity.vo.BlogArticleVo;

import java.util.List;

@Mapper
public interface BlogArticleMapper extends BaseMapper<BlogArticleEntity> {
    /**
     * 查询文章列表
     *
     * @param blogArticleDto
     * @return
     */
    List<BlogArticleVo> selectListBySearch(@Param("page") Page page, @Param("dto") BlogArticleDto blogArticleDto);

    /**
     * 查询文章列表(总条数)
     *
     * @param blogArticleDto
     * @return
     */
    Integer selectListCountBySearch(@Param("dto") BlogArticleDto blogArticleDto);


    /**
     * 查询单独文章
     *
     * @param blogArticleDto
     * @return
     */
    BlogArticleVo selectDetailById(@Param("dto") BlogArticleDetailDto blogArticleDto);

    /**
     * 查询上一页信息
     */
    BlogArticleLNBodyVo selectPrevArticle(@Param("dto") BlogArticleCommonDto blogArticleCommonDto);

    /**
     * 查询下一页文章
     */
    BlogArticleLNBodyVo selectNextArticle(@Param("dto") BlogArticleCommonDto blogArticleCommonDto);
}
