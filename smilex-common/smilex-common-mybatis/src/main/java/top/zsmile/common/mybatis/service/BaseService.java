package top.zsmile.common.mybatis.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.common.mybatis.meta.conditions.query.LambdaQueryWrapper;
import top.zsmile.common.mybatis.meta.conditions.query.QueryWrapper;
import top.zsmile.common.mybatis.meta.conditions.udpate.LambdaUpdateWrapper;
import top.zsmile.common.mybatis.meta.conditions.udpate.UpdateWrapper;
import top.zsmile.common.mybatis.utils.Constants;
import top.zsmile.common.mybatis.utils.PageQuery;
import top.zsmile.common.mybatis.utils.SqlHelper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseService<T> {

    /**
     * 默认批次提交数量
     */
    int DEFAULT_BATCH_SIZE = 1000;


    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    BaseMapper<T> getBaseMapper();

    /**
     * 根据 ID 查询
     *
     * @param id     主键ID
     * @param column 字段名
     */
    default T getById(Serializable id, String... column) {
        return getBaseMapper().selectById(id, column);
    }

    default T getObjByMap(Map<String, ? extends Object> map, String... columns) {
        return getBaseMapper().selectOneByMap(map, columns);
    }

    /**
     * 根据ID查询，可传入字段名查询需要得字段
     *
     * @param id      主键ID
     * @param columns 字段名
     * @return
     */
    default Map<String, Object> getMapById(Serializable id, String... columns) {
        return getBaseMapper().selectMapById(id, columns);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList  id列表
     * @param columns 字段名
     */
    default List<T> listByIds(Collection<? extends Serializable> idList, String... columns) {
        return getBaseMapper().selectByIds(idList, columns);
    }

    /**
     * 查询字段信息
     *
     * @param columns 字段名
     */
    default List<T> list(String... columns) {
        return getBaseMapper().selectListByMap(null, columns);
    }


    /**
     * 查询字段信息
     *
     * @param queryWrapper 查询条件
     */
    default List<T> list(QueryWrapper<T> queryWrapper) {
        return getBaseMapper().selectList(queryWrapper);
    }

//    /**
//     * 查询字段信息
//     *
//     * @param queryWrapper 查询条件
//     */
//    default List<T> list(LambdaQueryWrapper<T> queryWrapper) {
//        return getBaseMapper().selectList(queryWrapper);
//    }


    /**
     * 根据ID集合查询，可传入字段名查询需要得字段
     *
     * @param idList  id列表
     * @param columns 字段名
     * @return
     */
    default List<Map<String, Object>> listMapByIds(Collection<? extends Serializable> idList, String... columns) {
        return getBaseMapper().selectMapByIds(idList, columns);
    }

    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     *
     * @param columnMap 查询条件
     * @param columns   字段名
     * @return
     */
    default List<T> listByMap(Map<String, Object> columnMap, String... columns) {
        return getBaseMapper().selectListByMap(columnMap, columns);
    }

    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     *
     * @param columnMap 查询条件
     * @param columns   字段名
     * @return
     */
    default List<Map<String, Object>> listMapByMap(Map<String, Object> columnMap, String... columns) {
        return getBaseMapper().selectMapListByMap(columnMap, columns);
    }


    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param entity  实体对象封装操作类（可以为 null）
     * @param columns 字段名
     */
    default List<T> listByObj(T entity, String... columns) {
        return getBaseMapper().selectListByObj(entity, columns);
    }


    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param entity  实体对象封装操作类（可以为 null）
     * @param columns 字段名
     */
    default List<Map<String, Object>> listMapByObj(T entity, String... columns) {
        return getBaseMapper().selectListMapByObj(entity, columns);
    }

    /**
     * 根据ID更新
     *
     * @param entity
     * @return
     */
    default boolean updateById(T entity) {
        return SqlHelper.retBool(getBaseMapper().updateById(entity));
    }

    /**
     * 根据wrapper 更新
     *
     * @param wrapper
     * @return
     */
    default int update(UpdateWrapper<T> wrapper) {
        return getBaseMapper().update(wrapper);
    }

//    /**
//     * 根据wrapper 更新
//     *
//     * @param wrapper
//     * @return
//     */
//    default int update(LambdaUpdateWrapper<T> wrapper) {
//        return getBaseMapper().update(wrapper);
//    }

    /**
     * 插入数据，自动生成id
     *
     * @param entity
     * @return
     */
    boolean save(T entity);


    /**
     * 批量插入数据
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatch(Collection<? extends T> collection) {
        return saveBatch(collection, DEFAULT_BATCH_SIZE);
    }

    boolean saveBatch(Collection<? extends T> collection, int size);


    /**
     * 根据wrapper删除
     *
     * @param wrapper 条件
     * @return
     */
    default boolean remove(UpdateWrapper<T> wrapper) {
        return SqlHelper.retBool(getBaseMapper().delete(wrapper));
    }

    /**
     * 根据ID 逻辑删除
     *
     * @param id 主键ID
     * @return
     */
    default boolean removePhysicsById(Serializable id) {
        return SqlHelper.retBool(getBaseMapper().deletePhysicsById(id));
    }

    /**
     * 根据ID集合 批量逻辑删除
     *
     * @param ids 主键ID列表或实体列表(不能为 null 以及 empty)
     */
    default boolean removePhysicsByIds(Collection<? extends Serializable> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deletePhysicsByIds(ids));
    }

    /**
     * 根据 columnMap 条件，物理删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    default boolean removePhysicsByMap(Map<String, Object> columnMap) {
        if (CollectionUtils.isEmpty(columnMap)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deletePhysicsByMap(columnMap));
    }

    /**
     * 根据ID进行物理删除
     *
     * @param id
     * @return
     */
    default boolean removeById(Serializable id) {
        return SqlHelper.retBool(getBaseMapper().deleteById(id));
    }

    /**
     * 根据ID集合 批量逻辑删除
     *
     * @param idList 主键ID列表或实体列表(不能为 null 以及 empty)
     */
    default boolean removeByIds(Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deleteByIds(idList));
    }


    /**
     * 根据 columnMap 条件，逻辑删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    default boolean removeByMap(Map<String, Object> columnMap) {
        if (CollectionUtils.isEmpty(columnMap)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deleteByMap(columnMap));
    }


    /**
     * TODO 临时用来代替拦截器实现的分页功能，后续优化
     */
    default IPage<T> getPageByMap(Map<String, Object> columnMap, String... columns) {
        return getPageByMap(columnMap, false, columns);
    }


    /**
     * TODO 临时用来代替拦截器实现的分页功能，后续优化
     */
    default IPage<T> getPageByMap(Map<String, Object> columnMap, boolean isAll, String... columns) {
        IPage<T> page = new PageQuery<T>().getPage(columnMap);
        if (isAll) {
            page.setSize(Constants.PAGE_ALL_OFFSET);
        }
        List<T> list = getBaseMapper().selectListPageByMap(page, columnMap, columns);
        Long count = getBaseMapper().selectCountByMap(columnMap);
        page.setRecords(list);
        page.setTotal(count);
        return page;
    }


    /**
     * 查询某个字段的集合
     */
    default List<Object> getSingleByMap(Map<String, Object> cm, String column) {
        return getBaseMapper().selectSingleByMap(cm, column);
    }


}
