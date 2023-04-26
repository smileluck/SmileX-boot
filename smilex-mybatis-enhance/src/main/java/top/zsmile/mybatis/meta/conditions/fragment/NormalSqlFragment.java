package top.zsmile.mybatis.meta.conditions.fragment;

import top.zsmile.mybatis.meta.StringPool;
import top.zsmile.mybatis.meta.conditions.SqlKeyword;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 通用的单表条件
 *
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/24/9:42
 * @ClassName: NormalSqlFragment
 * @Description: NormalSqlFragment
 */
public class NormalSqlFragment extends AbstractSqlFragment {


    NormalSqlFragment() {
        this.needRefreshLastValue = true;
    }

    /**
     * @param list 检查列表
     * @return 是否继续
     */
    @Override
    public boolean checkList(List<ISqlFragment> list) {
        if (list.size() == 1) {
            // 目前只有 and 和 or 会进入这里
            ISqlFragment firstSqlFragment = list.get(0);
            boolean andTest = MatchFragment.AND.test(getLastValue());
            boolean orTest = MatchFragment.OR.test(getLastValue());
            if (andTest || orTest) {
                if (andTest && MatchFragment.AND.test(firstSqlFragment)) {
                    return false;
                } else if (orTest && MatchFragment.OR.test(firstSqlFragment)) {
                    return false;
                } else {
                    removeAndRefreshLastValue();
                }
            }
        } else {
            // 如果 不为空，同时最后一个不是 and 或者 or,则默认添加 and
            if (!MatchFragment.AND_OR.test(getLastValue())) {
                list.add(SqlKeyword.AND);
            }
        }
        return true;
    }

    @Override
    public String loopListSql() {
        if (MatchFragment.AND_OR.test(getLastValue())) {
            removeAndRefreshLastValue();
        }
        if (isEmpty()) {
            return StringPool.EMPTY;
        }
        return stream().map(ISqlFragment::getSqlFragment).collect(Collectors.joining(StringPool.SPACE, StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET));
    }
}
