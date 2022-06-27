package top.zsmile.dao;

import org.apache.ibatis.annotations.*;
import top.zsmile.core.exception.SXException;
import top.zsmile.meta.IPage;
import top.zsmile.provider.BaseDeleteProvider;
import top.zsmile.provider.BaseInsertProvider;
import top.zsmile.provider.BaseSelectProvider;
import top.zsmile.provider.BaseUpdateProvider;
import top.zsmile.utils.Constants;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {


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
    List<Map<String, Object>> selectMapBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> ids, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     *
     * @param cm
     * @param columns
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectListByMap")
    List<T> selectListByMap(Map<String, Object> cm, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     *
     * @param cm
     * @param columns
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectListByMap")
    List<Map<String, Object>> selectMapByMap(Map<String, Object> cm, @Param(Constants.COLUMNS) String... columns);


    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param entity 实体对象封装操作类（可以为 null）
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectList")
    List<T> selectList(@Param(Constants.ENTITY) T entity, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param entity 实体对象封装操作类（可以为 null）
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectList")
    List<Map<String, Object>> selectListMap(@Param(Constants.ENTITY) T entity, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据字段集合查询，查询总条数
     *
     * @param cm 实体对象封装操作类（可以为 null）
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectCount")
    Long selectCount(@Param(Constants.COLUMNS_MAP) Map<String, Object> cm);

    /**
     * 根据 entity 条件，查询一条记录
     * <p>查询一条记录，例如 qw.last("limit 1") 限制取一条记录, 注意：多条数据会报异常</p>
     *
     * @param cm 实体对象封装操作类（可以为 null）
     */
    default T selectOne(Map<String, Object> cm, String... column) {
        List<T> ts = this.selectListByMap(cm, column);
        if (ts != null && !ts.isEmpty()) {
            if (ts.size() != 1) {
                throw new SXException("One record is expected, but the query result is multiple records");
            }
            return ts.get(0);
        }
        return null;
    }

    /**
     * 根据 Wrapper 条件，判断是否存在记录
     *
     * @param cm 实体对象封装操作类
     * @return 是否存在记录
     */
    default boolean exists(Map<String, Object> cm) {
        Long count = this.selectCount(cm);
        return null != count && count > 0;
    }

    /**
     * 根据ID更新
     *
     * @param t
     * @return
     */
    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateById")
    int updateById(@Param(Constants.ENTITY) T t);


    /**
     * 插入数据
     *
     * @param t
     * @return
     */
    @InsertProvider(type = BaseInsertProvider.class, method = "insert")
    int insert(T t);

    /**
     * 根据ID 逻辑删除
     *
     * @param id 主键ID
     * @return
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deletePhysicsById")
    int deletePhysicsById(Serializable id);

    /**
     * 根据ID集合 批量逻辑删除
     *
     * @param idList 主键ID列表或实体列表(不能为 null 以及 empty)
     */
    @UpdateProvider(type = BaseDeleteProvider.class, method = "deletePhysicsBatchIds")
    int deletePhysicsBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);

    /**
     * 根据 cm 条件，物理删除记录
     *
     * @param cm 表字段 map 对象
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deletePhysicsByMap")
    int deletePhysicsByMap(Map<String, Object> cm);

    /**
     * 根据ID进行物理删除
     *
     * @param id
     * @return
     */
    @UpdateProvider(type = BaseDeleteProvider.class, method = "deleteLogicById")
    int deleteLogicById(Serializable id);

    /**
     * 根据ID集合 批量逻辑删除
     *
     * @param idList 主键ID列表或实体列表(不能为 null 以及 empty)
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteLogicBatchIds")
    int deleteLogicBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);


    /**
     * 根据 cm 条件，逻辑删除记录
     *
     * @param cm 表字段 map 对象
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteLogicByMap")
    int deleteLogicByMap(Map<String, Object> cm);


    /**
     * TODO 单表分页查询
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectPage")
    IPage<T> selectPage(IPage<T> page, Map<String, Object> cm, @Param(Constants.COLUMNS) String... columns);


    /**
     * 分页查询
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectPage")
    List<T> selectListPage(IPage<T> page, Map<String, Object> cm, String... columns);
}
