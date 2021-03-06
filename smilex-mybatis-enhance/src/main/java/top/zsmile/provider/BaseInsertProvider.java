package top.zsmile.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;
import top.zsmile.meta.TableInfo;
import top.zsmile.utils.Constants;
import top.zsmile.utils.ReflectUtils;
import top.zsmile.utils.TableQueryUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

public class BaseInsertProvider extends BaseProvider {

    public String insert(Object entity, ProviderContext context) {

        TableInfo tableInfo = getTableInfo(context);

        Field[] fields = tableInfo.getFields();

        return new SQL() {{
            INSERT_INTO(tableInfo.getTableName());
            INTO_COLUMNS(Stream.of(fields).filter(field -> !StringUtils.isEmpty(ReflectUtils.getFieldValue(entity, field.getName()))).map(TableQueryUtils::humpToLineName).toArray(String[]::new));
            INTO_VALUES(Stream.of(fields).filter(field -> !StringUtils.isEmpty(ReflectUtils.getFieldValue(entity, field.getName()))).map(TableQueryUtils::getInjectParameter).toArray(String[]::new));
        }}.toString();
    }


}
