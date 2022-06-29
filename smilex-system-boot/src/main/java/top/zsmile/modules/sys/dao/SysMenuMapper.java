package top.zsmile.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.dao.BaseMapper;
import top.zsmile.modules.sys.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {
    List<Map<String, Object>> selectMenusByUser();
}