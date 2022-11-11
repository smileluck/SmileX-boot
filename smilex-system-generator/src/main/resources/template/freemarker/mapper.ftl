package ${packages}.${moduleName}.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.mybatis.dao.BaseMapper;
import ${packages}.${moduleName}.entity.${bigHumpClass}Entity;

@Mapper
public interface ${bigHumpClass}Mapper extends BaseMapper<${bigHumpClass}Entity> {
}