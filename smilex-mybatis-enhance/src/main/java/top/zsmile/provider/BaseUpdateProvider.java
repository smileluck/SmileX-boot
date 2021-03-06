package top.zsmile.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.yaml.snakeyaml.scanner.Constant;
import top.zsmile.common.utils.StringPool;
import top.zsmile.meta.TableInfo;
import top.zsmile.utils.Constants;
import top.zsmile.utils.ReflectUtils;
import top.zsmile.utils.TableQueryUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class BaseUpdateProvider extends BaseProvider {

    /**
     * 根据主键ID更新数据
     *
     * @param context
     * @return
     */
    public String updateById(Object obj, ProviderContext context) {
//        Object obj = params.get(Constants.ENTITY);
        TableInfo tableInfo = getTableInfo(context);
        Field[] fields = tableInfo.getFields();
        String sql = new SQL() {{
            UPDATE(tableInfo.getTableName());
            SET(Stream.of(fields).filter(field -> ReflectUtils.getFieldValue(obj, field.getName()) != null && !tableInfo.getPrimaryColumn().equals(TableQueryUtils.humpToLineName(field)))
                    .map(TableQueryUtils::getAssignParameter).toArray(String[]::new));
            WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
        log.debug(sql);
        return sql;
    }
}
