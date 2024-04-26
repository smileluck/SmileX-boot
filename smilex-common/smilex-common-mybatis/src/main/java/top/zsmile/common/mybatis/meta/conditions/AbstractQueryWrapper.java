package top.zsmile.common.mybatis.meta.conditions;

import top.zsmile.common.mybatis.meta.SFunction;
import top.zsmile.common.mybatis.meta.conditions.query.LambdaQueryWrapper;
import top.zsmile.common.mybatis.meta.conditions.query.Query;

/**
 * @param <E>        注入的Entity实体
 * @param <R>        使用的COLUMN字段类型，主要为String和SFunciton
 * @param <Children> 当前实体，this
 */
public abstract class AbstractQueryWrapper<E, R, Children extends AbstractQueryWrapper<E, R, Children>> extends AbstractWrapper<E, R, Children>
        implements Query<Children, E, R> {

    protected SharedString sqlSelect = SharedString.of();

    @Override
    public String getSqlSelect() {
        return sqlSelect.getValue();
    }

}
