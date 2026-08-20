package top.zsmile.common.mybatis.meta.conditions.fragment;

import top.zsmile.common.mybatis.meta.StringPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * SqlFragment抽象类
 */
public abstract class AbstractSqlFragment extends ArrayList<ISqlFragment> implements ISqlFragment {

    /**
     * 数组最后一个索引的值
     */
    protected ISqlFragment lastValue = null;
    /**
     * SQL片段
     */
    private String sqlFragment = StringPool.EMPTY;
    /**
     * 是否缓存过SQL片段
     */
    private boolean cacheSqlFragment = true;
    /**
     * 需要刷新最后一个值
     */
    protected boolean needRefreshLastValue = false;

    @Override
    public String getSqlFragment() {
        if (cacheSqlFragment) {
            return sqlFragment;
        }
        this.sqlFragment = loopListSql();
        this.cacheSqlFragment = true;
        return sqlFragment;
    }

    /**
     * 重写 addAll 自定义添加方式
     *
     * @param c 容器
     * @return 是否添加成功
     */
    @Override
    public boolean addAll(Collection<? extends ISqlFragment> c) {
        List<ISqlFragment> list = new ArrayList<ISqlFragment>(c);
        boolean checkState = checkList(list);
        if (checkState && !list.isEmpty()) {
            this.cacheSqlFragment = false;
            this.sqlFragment = StringPool.EMPTY;
            boolean b = super.addAll(list);
            if (needRefreshLastValue) {
                refreshLastValue();
            }
            return b;
        }
        return false;
    }

    /**
     * 获取最后一个值
     */
    protected ISqlFragment getLastValue() {
        return lastValue;
    }

    /**
     * 刷新最后一个值
     */
    protected void refreshLastValue() {
        lastValue = isEmpty() ? null : get(size() - 1);
    }

    /**
     * 删除并刷新最后一个值（空列表安全）
     */
    protected void removeAndRefreshLastValue() {
        if (!isEmpty()) {
            remove(size() - 1);
        }
        refreshLastValue();
    }

    /**
     * 检查及更改列表，并返回检查状态
     *
     * @param list 检查列表
     * @return 检查状态
     */
    public abstract boolean checkList(List<ISqlFragment> list);

    /**
     * 遍历list获取SQL
     *
     * @return sql
     */
    public abstract String loopListSql();
}
