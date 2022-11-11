package top.zsmile.modules.blog.service.impl;

import top.zsmile.mybatis.service.BaseService;
import top.zsmile.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.modules.blog.entity.BlogSectionEntity;
import top.zsmile.modules.blog.dao.BlogSectionMapper;
import top.zsmile.modules.blog.service.BlogSectionService;
import org.springframework.stereotype.Service;

@Service("blogSectionService")
public class BlogSectionServiceImpl extends BaseServiceImpl<BlogSectionMapper,BlogSectionEntity> implements BlogSectionService {
}