package top.zsmile.modules.sys.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysLogEntity;
import top.zsmile.modules.sys.dao.SysLogMapper;
import top.zsmile.modules.sys.service.SysLogService;
import org.springframework.stereotype.Service;

@Service("sysLogService")
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper,SysLogEntity> implements SysLogService {
}