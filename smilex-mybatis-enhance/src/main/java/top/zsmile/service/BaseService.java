package top.zsmile.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.zsmile.dao.BaseMapper;
import top.zsmile.meta.IPage;
import top.zsmile.meta.Page;
import top.zsmile.utils.SqlHelper;

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
        return getBaseMapper().selectBatchIds(idList, columns);
    }


    /**
     * 根据ID集合查询，可传入字段名查询需要得字段
     *
     * @param idList  id列表
     * @param columns 字段名
     * @return
     */
    default List<Map<String, Object>> listMapByIds(Collection<? extends Serializable> idList, String... columns) {
        return getBaseMapper().selectMapBatchIds(idList, columns);
    }

    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     *
     * @param columnMap 查询条件
     * @param columns   字段名
     * @return
     */
    default List<T> getByMap(Map<String, Object> columnMap, String... columns) {
        return getBaseMapper().selectByMap(columnMap, columns);
    }

    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     *
     * @param columnMap 查询条件
     * @param columns   字段名
     * @return
     */
    default List<Map<String, Object>> getMapByMap(Map<String, Object> columnMap, String... columns) {
        return getBaseMapper().selectMapByMap(columnMap, columns);
    }

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param entity  实体对象封装操作类（可以为 null）
     * @param columns 字段名
     */
    default List<T> listByObj(T entity, String... columns) {
        return getBaseMapper().selectList(entity, columns);
    }


    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param entity  实体对象封装操作类（可以为 null）
     * @param columns 字段名
     */
    default List<Map<String, Object>> listMapByObj(T entity, String... columns) {
        return getBaseMapper().selectListMap(entity, columns);
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
     * 插入数据
     *
     * @param entity
     * @return
     */
    default boolean save(T entity) {
        return SqlHelper.retBool(getBaseMapper().insert(entity));
    }


    /**
     * TODO 批量插入数据
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatch(Collection<? extends T> collection) {
        return saveBatch(collection, DEFAULT_BATCH_SIZE);
    }

    boolean saveBatch(Collection<? extends T> collection, int size);


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
    default boolean removePhysicsBatchIds(Collection<? extends Serializable> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deletePhysicsBatchIds(ids));
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
    default boolean removeLogicById(Serializable id) {
        return SqlHelper.retBool(getBaseMapper().deleteLogicById(id));
    }

    /**
     * 根据ID集合 批量逻辑删除
     *
     * @param idList 主键ID列表或实体列表(不能为 null 以及 empty)
     */
    default boolean removeLogicBatchIds(Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deleteLogicBatchIds(idList));
    }


    /**
     * 根据 columnMap 条件，逻辑删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    default boolean removeLogicByMap(Map<String, Object> columnMap) {
        if (CollectionUtils.isEmpty(columnMap)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deleteLogicByMap(columnMap));
    }

}
