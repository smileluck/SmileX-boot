package top.zsmile.system.modules.blog.service.impl;

import top.zsmile.system.modules.open.entity.vo.BlogTagVo;
import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.blog.entity.BlogTagEntity;
import top.zsmile.system.modules.blog.dao.BlogTagMapper;
import top.zsmile.system.modules.blog.service.BlogTagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("blogTagService")
public class BlogTagServiceImpl extends BaseServiceImpl<BlogTagMapper, BlogTagEntity> implements BlogTagService {


    @Override
    public List<BlogTagVo> getRandomTagList(Long tenantId) {
        return getBaseMapper().selectRandomTagList(tenantId);
    }
}