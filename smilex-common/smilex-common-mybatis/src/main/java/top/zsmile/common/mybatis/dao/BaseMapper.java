package top.zsmile.common.mybatis.dao;

import org.apache.ibatis.annotations.*;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.mybatis.utils.Constants;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.query.LambdaQueryWrapper;
import top.zsmile.common.mybatis.meta.conditions.query.QueryWrapper;
import top.zsmile.common.mybatis.meta.conditions.update.LambdaUpdateWrapper;
import top.zsmile.common.mybatis.meta.conditions.update.UpdateWrapper;
import top.zsmile.common.mybatis.provider.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

    /**
     * 根据ID查询，可传入字段名查询需要的字段
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectById")
    T selectById(Serializable id, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据ID查询，可传入字段名查询需要得字段（Map 结果）
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectById")
    Map<String, Object> selectMapById(Serializable id, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据ID集合查询，可传入字段名查询需要得字段
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectBatchIds")
    List<T> selectByIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> ids, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据ID集合查询，可传入字段名查询需要得字段（Map 结果）
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectBatchIds")
    List<Map<String, Object>> selectMapByIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> ids, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectListByMap")
    List<T> selectListByMap(Map<String, ? extends Object> cm, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据字段集合查询，可传入字段名查询需要得字段（Map 结果）
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectListByMap")
    List<Map<String, Object>> selectMapListByMap(Map<String, Object> cm, @Param(Constants.COLUMNS) String... columns);

    /**
     * 查询某个字段的集合
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectSingleByMap")
    List<Object> selectSingleByMap(Map<String, Object> cm, String column);

    /**
     * 根据 entity 条件，查询全部记录（非空字段等值条件）
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectList")
    List<T> selectListByObj(@Param(Constants.ENTITY) T entity, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据 entity 条件，查询全部记录（Map 结果）
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectList")
    List<Map<String, Object>> selectListMapByObj(@Param(Constants.ENTITY) T entity, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据字段集合查询，查询总条数
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectCount")
    Long selectCountByMap(@Param(Constants.COLUMNS_MAP) Map<String, Object> cm);

    /**
     * 根据 map 条件查询一条记录，多条时抛异常
     */
    default T selectOneByMap(Map<String, ? extends Object> cm, String... column) {
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
     * 根据 Wrapper 条件查询列表
     */
    @SelectProvider(type = BaseSelectWrapperProvider.class, method = "selectList")
    List<T> selectList(@Param(StringPool.WRAPPER) QueryWrapper<T> wrapper);

    /**
     * 根据 Lambda 条件查询列表
     */
    @SelectProvider(type = BaseSelectWrapperProvider.class, method = "selectList")
    List<T> selectListByLambda(@Param(StringPool.WRAPPER) LambdaQueryWrapper<T> wrapper);

    /**
     * 根据 Wrapper 条件统计条数
     */
    @SelectProvider(type = BaseSelectWrapperProvider.class, method = "selectCount")
    Long selectCount(@Param(StringPool.WRAPPER) QueryWrapper<T> wrapper);

    /**
     * 根据 Lambda 条件统计条数
     */
    @SelectProvider(type = BaseSelectWrapperProvider.class, method = "selectCount")
    Long selectCountByLambda(@Param(StringPool.WRAPPER) LambdaQueryWrapper<T> wrapper);

    /**
     * 分页查询（map 条件），count 与 LIMIT 由分页拦截器处理
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectPage")
    List<T> selectListPageByMap(IPage<T> page, @Param(Constants.COLUMNS_MAP) Map<String, Object> cm, String... columns);

    /**
     * 分页查询（Wrapper 条件），count 与 LIMIT 由分页拦截器处理
     */
    @SelectProvider(type = BaseSelectWrapperProvider.class, method = "selectPage")
    List<T> selectPage(IPage<T> page, @Param(StringPool.WRAPPER) QueryWrapper<T> wrapper);

    /**
     * 分页查询（Lambda 条件），count 与 LIMIT 由分页拦截器处理
     */
    @SelectProvider(type = BaseSelectWrapperProvider.class, method = "selectPage")
    List<T> selectPageByLambda(IPage<T> page, @Param(StringPool.WRAPPER) LambdaQueryWrapper<T> wrapper);

    /**
     * 根据 Wrapper 条件判断是否存在记录
     */
    default boolean exists(Map<String, Object> cm) {
        Long count = this.selectCountByMap(cm);
        return null != count && count > 0;
    }

    /**
     * 根据ID更新（非空字段）
     */
    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateById")
    int updateById(T t);

    /**
     * 根据 Wrapper 条件更新
     */
    @UpdateProvider(type = BaseUpdateProvider.class, method = "update")
    int update(@Param(StringPool.WRAPPER) UpdateWrapper<T> wrapper);

    /**
     * 根据 Lambda 条件更新
     */
    @UpdateProvider(type = BaseUpdateProvider.class, method = "update")
    int updateByLambda(@Param(StringPool.WRAPPER) LambdaUpdateWrapper<T> wrapper);

    /**
     * 插入单条数据
     */
    @InsertProvider(type = BaseInsertProvider.class, method = "insert")
    int insert(T t);

    /**
     * 批量插入数据
     */
    @InsertProvider(type = BaseInsertProvider.class, method = "batchInsert")
    int batchInsert(@Param(Constants.COLLECTION) List<? extends T> list);

    /**
     * 根据ID 物理删除
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deletePhysicsById")
    int deletePhysicsById(Serializable id);

    /**
     * 根据ID集合 批量物理删除
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deletePhysicsBatchIds")
    int deletePhysicsByIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);

    /**
     * 根据 cm 条件，物理删除记录
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deletePhysicsByMap")
    int deletePhysicsByMap(@Param(Constants.COLUMNS_MAP) Map<String, Object> cm);

    /**
     * 根据条件删除，配置了逻辑删除则走逻辑删除
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "delete")
    int delete(@Param(StringPool.WRAPPER) UpdateWrapper<T> wrapper);

    /**
     * 根据条件删除（Lambda），配置了逻辑删除则走逻辑删除
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "delete")
    int deleteByLambda(@Param(StringPool.WRAPPER) LambdaUpdateWrapper<T> wrapper);

    /**
     * 根据ID进行逻辑删除，无逻辑删除字段则物理删除
     */
    @UpdateProvider(type = BaseDeleteProvider.class, method = "deleteById")
    int deleteById(Serializable id);

    /**
     * 根据ID集合 批量逻辑删除
     */
    @UpdateProvider(type = BaseDeleteProvider.class, method = "deleteBatchIds")
    int deleteByIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);

    /**
     * 根据 cm 条件，逻辑删除记录
     */
    @UpdateProvider(type = BaseDeleteProvider.class, method = "deleteByMap")
    int deleteByMap(@Param(Constants.COLUMNS_MAP) Map<String, Object> cm);
}
