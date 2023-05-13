package top.zsmile.common.mybatis.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.core.exception.SXException;
import top.zsmile.common.mybatis.meta.StringPool;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.meta.conditions.udpate.UpdateWrapper;
import top.zsmile.common.mybatis.utils.ReflectUtils;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

import java.lang.reflect.Field;
import java.util.stream.Stream;

@Slf4j
public class BaseUpdateProvider extends BaseProvider {

    /**
     * 根据主键ID更新数据
     *
     * @param context
     * @return
     */
    public String updateById(ProviderContext context, Object obj) {
//        Object obj = params.get(Constants.ENTITY);
        TableInfo tableInfo = getTableInfo(context);
        Field[] fields = tableInfo.getFields();
        String sql = new SQL() {{
            UPDATE(tableInfo.getTableName());
            SET(Stream.of(fields).filter(field -> ReflectUtils.getFieldValue(obj, field.getName()) != null && !tableInfo.getPrimaryColumn().equals(TableQueryUtils.humpToLineName(field)))
                    .map(TableQueryUtils::getAssignParameter).toArray(String[]::new));
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            WHERE(tableInfo.primaryColumnWhere());

        }}.toString();
        return sql;
    }


    /**
     * 根据Wrapper更新数据
     *
     * @param context
     * @return
     */
    public String update(ProviderContext context, @Param(StringPool.WRAPPER) UpdateWrapper wrapper) {
        String sqlSet = wrapper.getSqlSet();

        if (StringUtils.isBlank(sqlSet)) {
            throw new SXException("update需配置Set属性");
        }
        TableInfo tableInfo = getTableInfo(context);
        Field[] fields = tableInfo.getFields();
        String sql = new SQL() {{
            UPDATE(tableInfo.getTableName());
            SET(sqlSet);
            String whereSqlFragment = wrapper.getWhereSqlFragment();
            if (tableInfo.hasLogicDelColumn()) WHERE(tableInfo.logicDelColumnWhere());
            if (StringUtils.isNotBlank(whereSqlFragment)) WHERE(whereSqlFragment);
        }}.toString();
        return sql;
    }
}
