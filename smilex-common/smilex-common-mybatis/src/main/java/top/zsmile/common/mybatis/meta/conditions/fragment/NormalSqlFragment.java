package top.zsmile.common.mybatis.meta.conditions.fragment;

import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.SqlKeyword;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用的单表条件
 * <p>
 * 条件单元模型：每个单元以 trailing AND/OR 连接词结尾，最终拼接时移除末尾连接词；
 * 嵌套条件（and(Consumer)/or(Consumer)）以单个自带连接词前缀的片段加入。
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
            // 单片段：目前只有 and/or 连接词与嵌套片段会进入这里
            ISqlFragment firstSqlFragment = list.get(0);
            boolean andTest = MatchFragment.AND.test(getLastValue());
            boolean orTest = MatchFragment.OR.test(getLastValue());
            if (andTest || orTest) {
                if (MatchFragment.AND_OR.test(firstSqlFragment)) {
                    // 连接词替换：and().or() -> or
                    if ((andTest && MatchFragment.AND.test(firstSqlFragment))
                            || (orTest && MatchFragment.OR.test(firstSqlFragment))) {
                        return false;
                    }
                    removeAndRefreshLastValue();
                }
                // 非连接词片段（嵌套片段自带 " AND (" 前缀）：保留尾部连接词语义由片段自身承担，
                // 移除原尾部连接词避免重复
                if (!MatchFragment.AND_OR.test(firstSqlFragment)) {
                    removeAndRefreshLastValue();
                }
            }
        } else {
            // 条件单元：已有条件且末尾不是连接词时，为当前单元前置 AND
            if (!isEmpty() && !MatchFragment.AND_OR.test(getLastValue())) {
                list.add(0, SqlKeyword.AND);
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
        return stream().map(ISqlFragment::getSqlFragment)
                .collect(Collectors.joining(StringPool.SPACE, StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET));
    }
}
