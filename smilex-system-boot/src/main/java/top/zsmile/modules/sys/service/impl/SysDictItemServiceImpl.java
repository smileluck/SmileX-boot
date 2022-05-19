package top.zsmile.modules.sys.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysDictItemEntity;
import top.zsmile.modules.sys.dao.SysDictItemMapper;
import top.zsmile.modules.sys.service.SysDictItemService;
import org.springframework.stereotype.Service;

@Service("sysDictItemService")
public class SysDictItemServiceImpl extends BaseServiceImpl<SysDictItemMapper,SysDictItemEntity> implements SysDictItemService {
}