package top.zsmile.common.mybatis.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.meta.conditions.AbstractUpdateWrapper;
import top.zsmile.common.mybatis.utils.EntityAutoFill;
import top.zsmile.common.mybatis.utils.ReflectUtils;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BaseUpdateProvider extends BaseProvider {

    /**
     * 根据主键ID更新数据（非空字段参与 SET，主键与逻辑删除列除外）
     * <p>
     * 更新前由 EntityAutoFill 填充 updateTime/updateBy 并处理加密字段。
     */
    public String updateById(ProviderContext context, Object obj) {
        TableInfo tableInfo = getTableInfo(context);
        EntityAutoFill.fillUpdate(obj, tableInfo);

        List<String> sets = new ArrayList<>();
        for (Field field : tableInfo.getFields()) {
            String column = TableQueryUtils.humpToLineName(field.getName());
            if (column.equals(tableInfo.getPrimaryColumn())) {
                continue;
            }
            if (ReflectUtils.getFieldValue(obj, field) != null) {
                sets.add(TableQueryUtils.getAssignParameter(field));
            }
        }
        return new SQL() {{
            UPDATE(tableInfo.getTableName());
            SET(sets.toArray(new String[0]));
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
    }

    /**
     * 根据 Wrapper 条件更新数据
     */
    public String update(ProviderContext context, AbstractUpdateWrapper ew) {
        if (ew == null) {
            throw new SXException("update 的 wrapper 不能为空");
        }
        String sqlSet = ew.getSqlSet();
        if (StringUtils.isBlank(sqlSet)) {
            throw new SXException("update需配置Set属性");
        }
        TableInfo tableInfo = getTableInfo(context);
        String sql = new SQL() {{
            UPDATE(tableInfo.getTableName());
            SET(sqlSet);
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            String whereSqlFragment = ew.getWhereSqlFragment();
            if (StringUtils.isNotBlank(whereSqlFragment)) WHERE(whereSqlFragment);
        }}.toString();
        String lastSql = ew.getLastSql();
        return StringUtils.isBlank(lastSql) ? sql : sql + lastSql;
    }
}
