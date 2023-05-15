package top.zsmile.modules.sys.service.impl;

import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysDeptEntity;
import top.zsmile.modules.sys.dao.SysDeptMapper;
import top.zsmile.modules.sys.service.SysDeptService;
import org.springframework.stereotype.Service;

@Service("sysDeptService")
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper,SysDeptEntity> implements SysDeptService {
}