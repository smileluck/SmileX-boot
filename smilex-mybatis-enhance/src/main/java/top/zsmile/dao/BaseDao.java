package top.zsmile.dao;

import org.apache.ibatis.annotations.InsertProvider;
import top.zsmile.provider.BaseInsertProvider;

public interface BaseDao<T> {
    T selectById(Object id);

    int updateById(T t);

    @InsertProvider(type = BaseInsertProvider.class, method = "insert")
    int insert(T t);

    int deletePhysicsById(Object id);

    int deleteLogicById(Object id);


}
