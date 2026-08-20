package top.zsmile.common.mybatis.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.mybatis.meta.IPage;
import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.meta.conditions.AbstractQueryWrapper;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

/**
 * Wrapper 查询 SQL 生成
 * <p>
 * GROUP BY / HAVING / ORDER BY / last 由 wrapper 片段自带关键字前缀（含首尾空格），
 * 直接拼接在 SQL 尾部，避免 SQL builder 二次添加关键字产生 "ORDER BY ORDER BY"。
 */
public class BaseSelectWrapperProvider extends BaseProvider {

    /**
     * 根据 Wrapper 条件查询列表（支持 QueryWrapper 与 LambdaQueryWrapper）
     */
    public String selectList(ProviderContext context, @Param(StringPool.WRAPPER) final AbstractQueryWrapper ew) {
        TableInfo tableInfo = getTableInfo(context);
        requireWrapper(ew);
        String sql = new SQL() {{
            SELECT(selectColumn(tableInfo, ew));
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            String whereSqlFragment = ew.getWhereSqlFragment();
            if (StringUtils.isNotBlank(whereSqlFragment)) WHERE(whereSqlFragment);
        }}.toString();
        sql += ew.getGroupSqlFragment();
        sql += ew.getHavingSqlFragment();
        sql += ew.getOrderSqlFragment();
        sql += StringUtils.defaultString(ew.getLastSql());
        return TableQueryUtils.getSqlScript(sql);
    }

    /**
     * 根据 Wrapper 条件统计条数
     */
    public String selectCount(ProviderContext context, @Param(StringPool.WRAPPER) final AbstractQueryWrapper ew) {
        TableInfo tableInfo = getTableInfo(context);
        requireWrapper(ew);
        String sql = new SQL() {{
            SELECT(tableInfo.getCountColumn());
            FROM(tableInfo.getTableName());
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            String whereSqlFragment = ew.getWhereSqlFragment();
            if (StringUtils.isNotBlank(whereSqlFragment)) WHERE(whereSqlFragment);
        }}.toString();
        return TableQueryUtils.getSqlScript(sql);
    }

    /**
     * 根据 Wrapper 条件分页查询（SQL 不含分页子句，由分页拦截器处理 count 与 LIMIT）
     */
    public String selectPage(ProviderContext context, final IPage page, @Param(StringPool.WRAPPER) final AbstractQueryWrapper ew) {
        return selectList(context, ew);
    }

    private void requireWrapper(AbstractQueryWrapper ew) {
        if (ew == null) {
            throw new SXException("wrapper 不能为空");
        }
    }

    /**
     * select 列校验：白名单命中实体字段走 as 映射，否则做列名格式校验（防注入）
     */
    private String[] selectColumn(final TableInfo tableInfo, final AbstractQueryWrapper ew) {
        String sqlSelect = ew.getSqlSelect();
        if (StringUtils.isNotBlank(sqlSelect)) {
            String[] columns = sqlSelect.split(StringPool.COMMA);
            for (String column : columns) {
                if (tableInfo.hasWhereColumn(column.trim())) {
                    continue;
                }
                // 非实体字段（如聚合列）仅做格式白名单校验
                TableQueryUtils.checkSqlName(column.trim());
            }
            return columns;
        }
        return tableInfo.getSelectColumns();
    }
}
