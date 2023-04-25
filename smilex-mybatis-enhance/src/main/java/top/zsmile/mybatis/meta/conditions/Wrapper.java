package top.zsmile.mybatis.meta.conditions;

import top.zsmile.mybatis.meta.conditions.fragment.ISqlFragment;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/24/17:52
 * @ClassName: Wrapper
 * @Description: Wrapper
 */
public abstract class Wrapper<E> implements ISqlFragment {
    public abstract String getWhereSqlFragment();

    public abstract String getGroupSqlFragment();

    public abstract String getOrderSqlFragment();

    public abstract String getHavingSqlFragment();
}
