package top.zsmile.system.modules.sys.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.sys.entity.SysDictEntity;
import top.zsmile.system.modules.sys.dao.SysDictMapper;
import top.zsmile.system.modules.sys.service.SysDictService;
import org.springframework.stereotype.Service;

@Service("sysDictService")
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper,SysDictEntity> implements SysDictService {
}