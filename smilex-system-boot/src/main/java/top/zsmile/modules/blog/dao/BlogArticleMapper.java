package top.zsmile.modules.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zsmile.dao.BaseMapper;
import top.zsmile.meta.IPage;
import top.zsmile.meta.Page;
import top.zsmile.modules.blog.entity.BlogArticleEntity;
import top.zsmile.modules.open.entity.dto.BlogArticleDto;
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

    Integer selectListCountBySearch(@Param("dto") BlogArticleDto blogArticleDto);
}