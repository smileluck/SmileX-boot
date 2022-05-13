package top.zsmile.dao;

import org.apache.ibatis.annotations.*;
import top.zsmile.provider.*;
import top.zsmile.utils.Constants;

import java.io.Serializable;
import java.util.Collection;

public interface BaseDao<T> {


    /**
     * 根据ID查询，可传入字段名查询需要得字段
     *
     * @param id
     * @param columns
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectById")
    T selectById(Serializable id, @Param(Constants.COLUMNS) String... columns);


    @SelectProvider(type = BaseSelectProvider.class, method = "selectBatchIds")
    T selectBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> ids, @Param(Constants.COLUMNS) String... columns);

    /**
     * 根据ID跟新
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


}
