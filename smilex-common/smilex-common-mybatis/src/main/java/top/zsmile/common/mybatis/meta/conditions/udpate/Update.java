package top.zsmile.common.mybatis.meta.conditions.udpate;

import java.io.Serializable;

/**
 * @param <T> 当前对象
 * @param <E> Entity实体类
 * @param <R> 使用的COLUMN字段类型，主要为String和SFunciton
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/22/17:27
 * @ClassName: Update
 * @Description: Update
 */
public interface Update<T, E, R> extends Serializable {

    /**
     * 设置更改属性 set
     *
     * @param column 字段
     * @param obj    值
     * @return
     */
    default T set(R column, Object obj) {
        return set(true, column, obj);
    }


    /**
     * 设置更改属性 set
     *
     * @param condition 生效条件
     * @param column    字段
     * @param obj       值
     * @return
     */
    T set(boolean condition, R column, Object obj);

    /**
     * 获取Set Sql
     *
     * @return sql
     */
    String getSqlSet();
}
