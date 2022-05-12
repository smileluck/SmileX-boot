package top.zsmile.dao;

import org.apache.ibatis.annotations.*;
import top.zsmile.provider.BaseDeleteProvider;
import top.zsmile.provider.BaseInsertProvider;
import top.zsmile.provider.BaseSelectProvider;
import top.zsmile.provider.BaseUpdateProvider;

public interface BaseDao<T> {
    @SelectProvider(type = BaseSelectProvider.class, method = "selectById")
    T selectById(Object id);

    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateById")
    int updateById(T t);

    @InsertProvider(type = BaseInsertProvider.class, method = "insert")
    int insert(T t);

    @UpdateProvider(type = BaseDeleteProvider.class, method = "deletePhysicsById")
    int deletePhysicsById(Object id);

    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteLogicById")
    int deleteLogicById(Object id);


}
