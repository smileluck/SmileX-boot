package top.zsmile.system.boot.modules.blog.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.boot.modules.blog.entity.BlogSectionEntity;
import top.zsmile.system.boot.modules.blog.dao.BlogSectionMapper;
import top.zsmile.system.boot.modules.blog.service.BlogSectionService;
import org.springframework.stereotype.Service;

@Service("blogSectionService")
public class BlogSectionServiceImpl extends BaseServiceImpl<BlogSectionMapper,BlogSectionEntity> implements BlogSectionService {
}