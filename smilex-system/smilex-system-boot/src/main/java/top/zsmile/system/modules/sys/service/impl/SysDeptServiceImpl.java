package top.zsmile.system.modules.sys.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.system.modules.sys.entity.SysDeptEntity;
import top.zsmile.system.modules.sys.dao.SysDeptMapper;
import top.zsmile.system.modules.sys.service.SysDeptService;
import org.springframework.stereotype.Service;

@Service("sysDeptService")
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper,SysDeptEntity> implements SysDeptService {
}