package top.zsmile.common.mybatis.meta.conditions.fragment;

import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.conditions.SqlKeyword;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分组条件
 *
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/24/9:42
 * @ClassName: NormalSqlFragment
 * @Description: NormalSqlFragment
 */
public class GroupSqlFragment extends AbstractSqlFragment {

    @Override
    public boolean checkList(List list) {
        list.remove(0);
        return true;
    }

    @Override
    public String loopListSql() {
        return stream().map(ISqlFragment::getSqlFragment).collect(Collectors.joining(StringPool.SPACE, StringPool.SPACE + SqlKeyword.GROUP_BY, StringPool.SPACE));
    }
}
