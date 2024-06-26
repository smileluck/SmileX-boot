package top.zsmile.common.mybatis.meta.conditions.query;

import java.io.Serializable;

/**
 * Query配置 *
 *
 * @param <T> 当前对象
 * @param <E> Entity实体类
 * @param <R> 使用的COLUMN字段类型，主要为String和SFunciton
 */
public interface Query<T, E, R> extends Serializable {
    /**
     * 查询字段
     */
    T select(R... columns);


    /**
     * 获取select 的SQL
     *
     * @return
     */
    String getSqlSelect();
}
