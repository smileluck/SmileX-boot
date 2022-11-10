package top.zsmile.modules.blog.service;

import org.apache.ibatis.annotations.Param;
import top.zsmile.modules.open.entity.vo.BlogTagVo;
import top.zsmile.service.BaseService;
import top.zsmile.modules.blog.entity.BlogTagEntity;

import java.util.List;

public interface BlogTagService extends BaseService<BlogTagEntity> {
    /**
     * 获取十个随机标签
     * @param tenantId
     * @return
     */
    List<BlogTagVo> getRandomTagList(Long tenantId);
}