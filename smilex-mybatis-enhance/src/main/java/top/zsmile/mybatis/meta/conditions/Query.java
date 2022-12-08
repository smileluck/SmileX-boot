package top.zsmile.mybatis.meta.conditions;

import java.io.Serializable;

/**
 * @param <T> 当前对象
 * @param <E> Entity实体类
 * @param <R> 使用的COLUMN字段类型，主要为String和SFunciton
 */
public interface Query<T, E, R> extends Serializable {
    /**
     * 查询字段
     */
    T select(R... columns);
}
