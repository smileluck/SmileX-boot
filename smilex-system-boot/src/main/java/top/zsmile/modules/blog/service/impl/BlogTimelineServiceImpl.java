package top.zsmile.modules.blog.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.blog.entity.BlogTimelineEntity;
import top.zsmile.modules.blog.dao.BlogTimelineMapper;
import top.zsmile.modules.blog.service.BlogTimelineService;
import org.springframework.stereotype.Service;

@Service("blogTimelineService")
public class BlogTimelineServiceImpl extends BaseServiceImpl<BlogTimelineMapper,BlogTimelineEntity> implements BlogTimelineService {
}