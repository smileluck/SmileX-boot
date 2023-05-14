package top.zsmile.system.modules.blog.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.blog.entity.BlogTimelineEntity;
import top.zsmile.system.modules.blog.dao.BlogTimelineMapper;
import top.zsmile.system.modules.blog.service.BlogTimelineService;
import org.springframework.stereotype.Service;

@Service("blogTimelineService")
public class BlogTimelineServiceImpl extends BaseServiceImpl<BlogTimelineMapper,BlogTimelineEntity> implements BlogTimelineService {
}