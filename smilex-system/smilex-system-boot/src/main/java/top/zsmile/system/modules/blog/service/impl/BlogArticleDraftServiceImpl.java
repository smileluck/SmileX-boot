package top.zsmile.system.modules.blog.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.blog.entity.BlogArticleDraftEntity;
import top.zsmile.system.modules.blog.dao.BlogArticleDraftMapper;
import top.zsmile.system.modules.blog.service.BlogArticleDraftService;
import org.springframework.stereotype.Service;

@Service("blogArticleDraftService")
public class BlogArticleDraftServiceImpl extends BaseServiceImpl<BlogArticleDraftMapper,BlogArticleDraftEntity> implements BlogArticleDraftService {
}