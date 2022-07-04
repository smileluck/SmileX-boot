package top.zsmile.modules.blog.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.blog.entity.BlogTagEntity;
import top.zsmile.modules.blog.dao.BlogTagMapper;
import top.zsmile.modules.blog.service.BlogTagService;
import org.springframework.stereotype.Service;

@Service("blogTagService")
public class BlogTagServiceImpl extends BaseServiceImpl<BlogTagMapper,BlogTagEntity> implements BlogTagService {
}