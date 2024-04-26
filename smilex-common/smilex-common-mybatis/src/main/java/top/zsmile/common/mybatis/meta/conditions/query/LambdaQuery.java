package top.zsmile.common.mybatis.meta.conditions.query;

import top.zsmile.common.mybatis.meta.SFunction;

import java.util.List;

/**
 * lambda Query配置
 *
 * @param <T> 当前对象
 * @param <E> Entity实体类
 * @param <R> 使用的COLUMN字段类型，主要为String和SFunciton
 */
public interface LambdaQuery<T, E, R> extends Query<T, E, R> {

//    List<E> list();
}
