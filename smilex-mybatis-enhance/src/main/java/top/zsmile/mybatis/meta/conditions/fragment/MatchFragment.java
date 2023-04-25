package top.zsmile.mybatis.meta.conditions.fragment;

import top.zsmile.mybatis.meta.conditions.SqlKeyword;

import java.util.function.Predicate;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/25/11:10
 * @ClassName: MatchFragment
 * @Description: MatchFragment
 */
public enum MatchFragment {
    AND_OR((i) -> SqlKeyword.AND == i || SqlKeyword.OR == i),
    AND((i) -> SqlKeyword.AND == i),
    OR((i) -> SqlKeyword.OR == i),
    GROUP_BY((i) -> SqlKeyword.GROUP_BY == i),
    ORDER_BY((i) -> SqlKeyword.ORDER_BY == i),
    HAVING((i) -> SqlKeyword.HAVING == i),
    ;

    private Predicate<ISqlFragment> predicate;

    public Predicate<ISqlFragment> getPredicate() {
        return predicate;
    }

    public boolean test(ISqlFragment iSqlFragment) {
        return getPredicate().test(iSqlFragment);
    }

    private MatchFragment(Predicate<ISqlFragment> predicate) {
        this.predicate = predicate;
    }
}
