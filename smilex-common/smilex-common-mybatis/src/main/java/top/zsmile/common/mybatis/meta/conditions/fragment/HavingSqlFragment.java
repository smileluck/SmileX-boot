package top.zsmile.common.mybatis.meta.conditions.fragment;

import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.SqlKeyword;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 聚合筛选条件片段
 * <p>
 * 每次调用对应一个完整条件（如 "COUNT(*) > 5"），多次调用以 AND 连接；
 * 关键字前缀由本片段自带，Provider 不得重复添加。
 */
public class HavingSqlFragment extends AbstractSqlFragment {

    @Override
    public boolean checkList(List<ISqlFragment> list) {
        if (!list.isEmpty() && MatchFragment.HAVING.test(list.get(0))) {
            list.remove(0);
        }
        // 已有条件时补 AND 连接
        if (!list.isEmpty() && !isEmpty()) {
            list.add(0, SqlKeyword.AND);
        }
        return !list.isEmpty();
    }

    @Override
    public String loopListSql() {
        if (isEmpty()) {
            return StringPool.EMPTY;
        }
        return stream().map(ISqlFragment::getSqlFragment)
                .collect(Collectors.joining(StringPool.SPACE, StringPool.SPACE + SqlKeyword.HAVING.getSqlFragment() + StringPool.SPACE, StringPool.SPACE));
    }
}
