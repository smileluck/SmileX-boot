package top.zsmile.modules.blog.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.blog.entity.BlogArticleDraftEntity;
import top.zsmile.modules.blog.dao.BlogArticleDraftMapper;
import top.zsmile.modules.blog.service.BlogArticleDraftService;
import org.springframework.stereotype.Service;

@Service("blogArticleDraftService")
public class BlogArticleDraftServiceImpl extends BaseServiceImpl<BlogArticleDraftMapper,BlogArticleDraftEntity> implements BlogArticleDraftService {
}