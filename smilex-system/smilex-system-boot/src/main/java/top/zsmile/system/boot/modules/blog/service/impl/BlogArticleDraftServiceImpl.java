package top.zsmile.system.boot.modules.blog.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.boot.modules.blog.entity.BlogArticleDraftEntity;
import top.zsmile.system.boot.modules.blog.dao.BlogArticleDraftMapper;
import top.zsmile.system.boot.modules.blog.service.BlogArticleDraftService;
import org.springframework.stereotype.Service;

@Service("blogArticleDraftService")
public class BlogArticleDraftServiceImpl extends BaseServiceImpl<BlogArticleDraftMapper,BlogArticleDraftEntity> implements BlogArticleDraftService {
}