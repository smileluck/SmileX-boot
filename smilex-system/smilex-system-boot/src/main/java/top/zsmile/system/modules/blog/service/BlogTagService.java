package top.zsmile.system.modules.blog.service;

import top.zsmile.system.modules.open.entity.vo.BlogTagVo;
import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.system.modules.blog.entity.BlogTagEntity;

import java.util.List;

public interface BlogTagService extends BaseService<BlogTagEntity> {
    /**
     * 获取十个随机标签
     * @param tenantId
     * @return
     */
    List<BlogTagVo> getRandomTagList(Long tenantId);
}