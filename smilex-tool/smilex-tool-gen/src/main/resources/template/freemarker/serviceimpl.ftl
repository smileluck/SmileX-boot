package ${packages}.${moduleName}.service.impl;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import ${packages}.${moduleName}.entity.${bigHumpClass}Entity;
import ${packages}.${moduleName}.dao.${bigHumpClass}Mapper;
import ${packages}.${moduleName}.service.${bigHumpClass}Service;
import org.springframework.stereotype.Service;

@Service("${smallHumpClass}Service")
public class ${bigHumpClass}ServiceImpl extends BaseServiceImpl<${bigHumpClass}Mapper,${bigHumpClass}Entity> implements ${bigHumpClass}Service {
}