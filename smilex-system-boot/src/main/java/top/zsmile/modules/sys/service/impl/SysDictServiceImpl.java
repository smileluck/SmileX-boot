package top.zsmile.modules.sys.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysDictEntity;
import top.zsmile.modules.sys.dao.SysDictMapper;
import top.zsmile.modules.sys.service.SysDictService;
import org.springframework.stereotype.Service;

@Service("sysDictService")
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper,SysDictEntity> implements SysDictService {
}