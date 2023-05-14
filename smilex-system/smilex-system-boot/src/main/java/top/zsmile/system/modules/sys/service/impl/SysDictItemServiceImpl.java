package top.zsmile.system.modules.sys.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.sys.entity.SysDictItemEntity;
import top.zsmile.system.modules.sys.dao.SysDictItemMapper;
import top.zsmile.system.modules.sys.service.SysDictItemService;
import org.springframework.stereotype.Service;

@Service("sysDictItemService")
public class SysDictItemServiceImpl extends BaseServiceImpl<SysDictItemMapper,SysDictItemEntity> implements SysDictItemService {
}