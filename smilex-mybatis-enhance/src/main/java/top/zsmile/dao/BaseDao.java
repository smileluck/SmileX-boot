package top.zsmile.dao;

import org.apache.ibatis.annotations.*;
import top.zsmile.core.exception.SXException;
import top.zsmile.provider.*;
import top.zsmile.utils.Constants;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseDao<T> {


    /**
     * 根据ID查询，可传入字段名查询需要的字段
     *
     * @param id
     * @param columns
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectById")
    T selectById(Serializable id, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据ID查询，可传入字段名查询需要得字段
     *
     * @param id
     * @param columns
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectById")
    Map<String, Object> selectMapById(Serializable id, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据ID集合查询，可传入字段名查询需要得字段
     *
     * @param ids
     * @param columns
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectBatchIds")
    List<T> selectBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> ids, @Param(Constants.COLUMNS) String... columns);


    /**
     * 根据ID集合查询，可传入字段名查询需要得字段
     *
     * @param ids
     * @param columns
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectBatchIds")
    Map<String, Object> selectMapBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> ids, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     *
     * @param columnMap
     * @param columns
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectByMap")
    List<T> selectByMap(@Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     *
     * @param columnMap
     * @param columns
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectByMap")
    List<Map<String, Object>> selectMapByMap(@Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap, @Param(Constants.COLUMNS) String... columns);


    /**
     * TODO 根据 entity 条件，查询全部记录
     *
     * @param columnMap 实体对象封装操作类（可以为 null）
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectList")
    List<T> selectList(@Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap, @Param(Constants.COLUMNS) String... columns);

    /**
     * TODO 根据 entity 条件，查询全部记录
     *
     * @param columnMap 实体对象封装操作类（可以为 null）
     */
    List<Map<String, Object>> selectListMap(@Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap, @Param(Constants.COLUMNS) String... columns);

    /**
     * TODO 根据 Wrapper 条件，查询总记录数
     *
     * @param columnMap 实体对象封装操作类（可以为 null）
     */
    Long selectCount(@Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap, @Param(Constants.COLUMNS) String... columns);

    /**
     * TODO 根据 entity 条件，查询一条记录
     * <p>查询一条记录，例如 qw.last("limit 1") 限制取一条记录, 注意：多条数据会报异常</p>
     *
     * @param columnMap 实体对象封装操作类（可以为 null）
     */
    default T selectOne(@Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap) {
        List<T> ts = this.selectList(columnMap);
        if (ts == null || ts.isEmpty()) {
            if (ts.size() != 1) {
                throw new SXException("One record is expected, but the query result is multiple records");
            }
            return ts.get(0);
        }
        return null;
    }

    /**
     * TODO 根据 Wrapper 条件，判断是否存在记录
     *
     * @param columnMap 实体对象封装操作类
     * @return 是否存在记录
     */
    default boolean exists(@Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap) {
        Long count = this.selectCount(columnMap);
        return null != count && count > 0;
    }

    /**
     * 根据ID更新
     *
     * @param t
     * @return
     */
    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateById")
    int updateById(T t);


    /**
     * 插入数据
     *
     * @param t
     * @return
     */
    @InsertProvider(type = BaseInsertProvider.class, method = "insert")
    int insert(T t);

    /**
     * 根据ID继续逻辑删除
     *
     * @param id
     * @return
     */
    @UpdateProvider(type = BaseDeleteProvider.class, method = "deletePhysicsById")
    int deletePhysicsById(Serializable id);

    /**
     * 根据ID进行物理删除
     *
     * @param id
     * @return
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteLogicById")
    int deleteLogicById(Serializable id);

    /**
     * TODO 删除（根据ID或实体 批量删除）
     *
     * @param idList 主键ID列表或实体列表(不能为 null 以及 empty)
     */
    int deleteBatchIds(@Param(Constants.COLLECTION) Collection<?> idList);


    /**
     * TODO 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    int deleteByMap(@Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap);


    /**
     * TODO 根据 Wrapper 条件，查询全部记录
     *
     * @param columnMap 实体对象封装操作类（可以为 null）
     */
    List<Map<String, Object>> selectMaps(@Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap);

}
