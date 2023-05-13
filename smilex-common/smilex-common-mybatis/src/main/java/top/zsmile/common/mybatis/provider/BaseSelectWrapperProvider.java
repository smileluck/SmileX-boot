package top.zsmile.common.mybatis.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.common.mybatis.holder.TenantHolder;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.meta.conditions.query.QueryWrapper;
import top.zsmile.common.mybatis.utils.Constants;
import top.zsmile.common.mybatis.utils.ReflectUtils;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

public class BaseSelectWrapperProvider extends BaseProvider {

    /**
     * 根据对象entity查询不为null的数据，可传入字段名查询需要得字段
     *
     * @param context      实体
     * @param queryWrapper 查询条件
     * @return
     */
    public String selectList(ProviderContext context, @Param(StringPool.WRAPPER) final QueryWrapper queryWrapper) {
        TableInfo tableInfo = getTableInfo(context);
        String s = new SQL() {{
            SELECT(selectColumn(tableInfo, queryWrapper));
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            if (tableInfo.hasTenantColumn() && TenantHolder.isIgnore()) WHERE(tableInfo.tenantColumnWhere());
            if (queryWrapper != null) {
                String whereSqlFragment = queryWrapper.getWhereSqlFragment();
                String groupSqlFragment = queryWrapper.getGroupSqlFragment();
                String havingSqlFragment = queryWrapper.getHavingSqlFragment();
                String orderSqlFragment = queryWrapper.getOrderSqlFragment();
                if (StringUtils.isNotBlank(whereSqlFragment)) WHERE(whereSqlFragment);
                if (StringUtils.isNotBlank(groupSqlFragment)) GROUP_BY(groupSqlFragment);
                if (StringUtils.isNotBlank(havingSqlFragment)) HAVING(havingSqlFragment);
                if (StringUtils.isNotBlank(orderSqlFragment)) ORDER_BY(orderSqlFragment);
            }
        }}.toString();
        return TableQueryUtils.getSqlScript(s);
    }


    private String[] selectColumn(final TableInfo tableInfo, final QueryWrapper queryWrapper) {
        if (StringUtils.isNotBlank(queryWrapper.getSqlSelect())) {
            return queryWrapper.getSqlSelect().split(StringPool.COMMA);
        }
        return tableInfo.getSelectColumns();
    }
}
