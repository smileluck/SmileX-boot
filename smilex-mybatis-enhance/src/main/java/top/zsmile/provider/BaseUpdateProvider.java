package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.meta.TableInfo;
import top.zsmile.utils.ReflectUtils;
import top.zsmile.utils.TableQueryUtils;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class BaseUpdateProvider extends BaseProvider {

    /**
     * 根据主键ID更新数据
     *
     * @param context
     * @return
     */
    public String updateById(Object obj, ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        Field[] fields = tableInfo.getFields();

        return new SQL() {{
            UPDATE(tableInfo.getTableName());
            SET(Stream.of(fields).filter(field -> ReflectUtils.getFieldValue(obj, field.getName()) != null && !tableInfo.getPrimaryColumn().equals(TableQueryUtils.humpToLineName(field)))
                    .map(TableQueryUtils::getAssignParameter).toArray(String[]::new));
            WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
    }
}
