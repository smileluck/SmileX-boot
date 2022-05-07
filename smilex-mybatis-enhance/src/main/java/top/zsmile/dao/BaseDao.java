package top.zsmile.dao;

public interface BaseDao<T> {
    T selectById(Object id);

    T deleteById(Object id);

    T updateById(T t);
}
