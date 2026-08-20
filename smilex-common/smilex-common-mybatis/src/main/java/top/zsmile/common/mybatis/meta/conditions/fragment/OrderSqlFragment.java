package top.zsmile.common.mybatis.meta.conditions.fragment;

import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.SqlKeyword;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 排序条件片段
 * <p>
 * 每次 orderBy 调用对应一个完整排序片段（如 "a ASC, b DESC"），
 * 多次调用以逗号连接；关键字前缀由本片段自带，Provider 不得重复添加。
 */
public class OrderSqlFragment extends AbstractSqlFragment {

    @Override
    public boolean checkList(List<ISqlFragment> list) {
        // 移除路由用关键字片段
        if (!list.isEmpty() && MatchFragment.ORDER_BY.test(list.get(0))) {
            list.remove(0);
        }
        return !list.isEmpty();
    }

    @Override
    public String loopListSql() {
        if (isEmpty()) {
            return StringPool.EMPTY;
        }
        return stream().map(ISqlFragment::getSqlFragment)
                .collect(Collectors.joining(StringPool.COMMA, StringPool.SPACE + SqlKeyword.ORDER_BY.getSqlFragment() + StringPool.SPACE, StringPool.SPACE));
    }
}
