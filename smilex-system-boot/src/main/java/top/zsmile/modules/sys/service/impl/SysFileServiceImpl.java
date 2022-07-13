package top.zsmile.modules.sys.service.impl;

import top.zsmile.service.BaseService;
import top.zsmile.service.impl.BaseServiceImpl;
import top.zsmile.modules.sys.entity.SysFileEntity;
import top.zsmile.modules.sys.dao.SysFileMapper;
import top.zsmile.modules.sys.service.SysFileService;
import org.springframework.stereotype.Service;

@Service("sysFileService")
public class SysFileServiceImpl extends BaseServiceImpl<SysFileMapper,SysFileEntity> implements SysFileService {
}