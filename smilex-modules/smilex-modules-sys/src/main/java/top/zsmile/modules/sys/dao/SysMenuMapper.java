package top.zsmile.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.modules.sys.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {
    /**
     * 获取用户的菜单
     *
     * @param userId
     * @return
     */
    List<Map<String, Object>> selectMenusByUser(Long userId);
    /**
     * 获取用户的菜单(超管使用)
     *
     * @return
     */
    List<Map<String, Object>> selectMenus();


    /**
     * 获取用户的权限
     */
    List<Object> selectPermsByUser(Long userId);
}