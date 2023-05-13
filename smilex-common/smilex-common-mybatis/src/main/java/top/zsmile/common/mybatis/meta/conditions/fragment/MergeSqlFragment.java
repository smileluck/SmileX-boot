package top.zsmile.common.mybatis.meta.conditions.fragment;

import top.zsmile.common.mybatis.meta.StringPool;

import java.util.Arrays;
import java.util.List;

/**
 * 合并处理SQL片段
 *
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/22/18:23
 * @ClassName: MergeSqlFragment
 * @Description: MergeSqlFragment
 */
public class MergeSqlFragment implements ISqlFragment {

    /**
     * 通用条件
     */
    private NormalSqlFragment normalSqlFragment = new NormalSqlFragment();
    /**
     * 分组
     */
    private GroupSqlFragment groupSqlFragment = new GroupSqlFragment();
    /**
     * 排序
     */
    private OrderSqlFragment orderSqlFragment = new OrderSqlFragment();
    /**
     * 聚合筛选
     */
    private HavingSqlFragment havingSqlFragment = new HavingSqlFragment();

    /**
     * SQL片段
     */
    private String sqlFragment = StringPool.EMPTY;
    /**
     * 是否缓存过SQL片段
     */
    private boolean cacheSqlFragment = true;

    /**
     * 分类添加到对应的SQL里面
     *
     * @param sqlFragments
     */
    public void add(ISqlFragment... sqlFragments) {
        if (sqlFragments.length >= 0) {
            List<ISqlFragment> iSqlFragments = Arrays.asList(sqlFragments);
            ISqlFragment firstSqlFragment = iSqlFragments.get(0);
            if (MatchFragment.GROUP_BY.test(firstSqlFragment)) {
                // 分组
                groupSqlFragment.addAll(iSqlFragments);
            } else if (MatchFragment.ORDER_BY.test(firstSqlFragment)) {
                // 排序
                orderSqlFragment.addAll(iSqlFragments);
            } else if (MatchFragment.HAVING.test(firstSqlFragment)) {
                // 聚合筛选
                havingSqlFragment.addAll(iSqlFragments);
            } else {
                // 否则为通用条件
                normalSqlFragment.addAll(iSqlFragments);
            }
            cacheSqlFragment = false;
        }
    }

    /**
     * 获取sql片段
     *
     * @return sql片段
     */
    @Override
    public String getSqlFragment() {
        if (cacheSqlFragment) {
            return sqlFragment;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(getNormalSql());
        builder.append(getGroupSql());
        builder.append(getHavingSql());
        builder.append(getOrderSql());

        sqlFragment = builder.toString();
        cacheSqlFragment = true;
        return sqlFragment;
    }

    public String getNormalSql() {
        if (!normalSqlFragment.isEmpty()) {
            return normalSqlFragment.getSqlFragment();
        }
        return StringPool.EMPTY;
    }

    public String getGroupSql() {
        if (!groupSqlFragment.isEmpty()) {
            return groupSqlFragment.getSqlFragment();
        }
        return StringPool.EMPTY;
    }

    public String getHavingSql() {
        if (!havingSqlFragment.isEmpty()) {
            return havingSqlFragment.getSqlFragment();
        }
        return StringPool.EMPTY;
    }

    public String getOrderSql() {
        if (!orderSqlFragment.isEmpty()) {
            return orderSqlFragment.getSqlFragment();
        }
        return StringPool.EMPTY;
    }
}
