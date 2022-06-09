package top.zsmile.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import top.zsmile.meta.TableInfo;
import top.zsmile.utils.Constants;
import top.zsmile.utils.ReflectUtils;
import top.zsmile.utils.TableQueryUtils;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class BaseInsertProvider extends BaseProvider {

    public String insert(ProviderContext context, @Param(Constants.ENTITY) Object entity) {
        TableInfo tableInfo = getTableInfo(context);

        Field[] fields = tableInfo.getFields();

        return new SQL() {{
            INSERT_INTO(tableInfo.getTableName());
            INTO_COLUMNS(Stream.of(fields).filter(field -> ReflectUtils.getFieldValue(entity, field.getName()) != null).map(TableQueryUtils::humpToLineName).toArray(String[]::new));
            INTO_VALUES(Stream.of(fields).filter(field -> ReflectUtils.getFieldValue(entity, field.getName()) != null).map(TableQueryUtils::getInjectParameter).toArray(String[]::new));
        }}.toString();
    }


}
