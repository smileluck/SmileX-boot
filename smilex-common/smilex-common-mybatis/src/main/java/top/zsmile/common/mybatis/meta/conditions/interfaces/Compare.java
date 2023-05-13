package top.zsmile.common.mybatis.meta.conditions.interfaces;

import java.util.Collection;

/**
 * 查询条件封装
 *
 * @param <R>        使用的COLUMN字段类型，主要为String和SFunciton
 * @param <Children> 当前实体，this
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/22/17:43
 * @ClassName: Compare
 * @Description: Compare
 */

public interface Compare<Children, R> {

    /**
     * 等于 =
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children eq(R column, Object obj);

    /**
     * 不等于 !=
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children ne(R column, Object obj);

    /**
     * 大于 >
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children gt(R column, Object obj);

    /**
     * 小于 <
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children lt(R column, Object obj);


    /**
     * 大于等于 >=
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children ge(R column, Object obj);

    /**
     * 小于等于 <=
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children le(R column, Object obj);

    /**
     * 两者之间 left<=x<=right
     *
     * @param column 字段
     * @param left   数据
     * @param right  数据
     * @return
     */
    Children between(R column, Object left, Object right);


    /**
     * 不在两者之间 left>x && x>right
     *
     * @param column 字段
     * @param left   数据
     * @param right  数据
     * @return
     */
    Children notBetween(R column, Object left, Object right);


    /**
     * 模糊查询 like %str%
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children like(R column, Object obj);


    /**
     * 左模糊查询 like %str
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children likeLeft(R column, Object obj);

    /**
     * 右模糊查询 like str%
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children likeRight(R column, Object obj);


    /**
     * 模糊查询 not like %str%
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children notLike(R column, Object obj);


    /**
     * 左模糊查询 not like %str
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children notLikeLeft(R column, Object obj);

    /**
     * 右模糊查询 not like str%
     *
     * @param column 字段
     * @param obj    数据
     * @return
     */
    Children notLikeRight(R column, Object obj);


    /**
     * 字段 is null
     *
     * @param column 字段
     * @return
     */
    Children isNull(R column);

    /**
     * 字段 is not null
     *
     * @param column 字段
     * @return
     */
    Children isNotNull(R column);

    /**
     * 包含 in
     *
     * @param column 字段
     * @param values 容器数组
     * @return
     */
    Children in(R column, Collection<?> values);


    /**
     * 包含 in
     *
     * @param column 字段
     * @param values 容器数组
     * @return
     */
    Children in(R column, Object... values);


    /**
     * 不包含 not in
     *
     * @param column 字段
     * @param values 容器数组
     * @return
     */
    Children notIn(R column, Collection<?> values);

    /**
     * 不包含 not in
     *
     * @param column 字段
     * @param values 容器数组
     * @return
     */
    Children notIn(R column, Object... values);


    /**
     * 分组 group by column...
     *
     * @param column 字段
     * @return
     */
    Children groupBy(R... column);

    /**
     * 排序 order by
     *
     * @param isAsc  是否顺序
     * @param column 字段
     * @return
     */
    Children orderBy(boolean isAsc, R... column);

    /**
     * 排序顺序 order by column... asc
     *
     * @param column 字段
     * @return
     */
    Children orderByAsc(R... column);


    /**
     * 排序倒序 order by column... desc
     *
     * @param column 字段
     * @return
     */
    Children orderByDesc(R... column);

    /**
     * 并条件 and
     *
     * @return
     */
    Children and();

    /**
     * 或条件 or
     *
     * @return
     */
    Children or();

}
