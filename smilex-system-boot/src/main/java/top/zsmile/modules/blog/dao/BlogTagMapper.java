package top.zsmile.modules.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zsmile.dao.BaseMapper;
import top.zsmile.modules.blog.entity.BlogTagEntity;
import top.zsmile.modules.open.entity.vo.BlogTagVo;

import java.util.List;

@Mapper
public interface BlogTagMapper extends BaseMapper<BlogTagEntity> {
    /**
     * 获取随机标签
     *
     * @param tenantId
     * @return
     */
    List<BlogTagVo> selectRandomTagList(@Param("tenantId") Long tenantId);
}