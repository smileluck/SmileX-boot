package top.zsmile.common.mybatis.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.utils.EntityAutoFill;
import top.zsmile.common.mybatis.utils.ReflectUtils;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class BaseInsertProvider extends BaseProvider {

    /**
     * 插入单条数据
     * <p>
     * 插入前由 EntityAutoFill 统一预填：雪花主键、租户ID、delFlag=0、审计字段、加密字段。
     * 空值列不进入 INSERT 列表，交由数据库默认值。
     */
    public String insert(Object entity, ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        EntityAutoFill.fillInsert(entity, tableInfo);

        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (Field field : tableInfo.getFields()) {
            if (!StringUtils.isEmpty(ReflectUtils.getFieldValue(entity, field))) {
                columns.add(TableQueryUtils.humpToLineName(field.getName()));
                values.add(TableQueryUtils.getInjectParameter(field));
            }
        }
        return new SQL() {{
            INSERT_INTO(tableInfo.getTableName());
            INTO_COLUMNS(columns.toArray(new String[0]));
            INTO_VALUES(values.toArray(new String[0]));
        }}.toString();
    }

    /**
     * 批量插入数据
     * <p>
     * 多行 VALUES 结构要求每行列集一致，因此全字段插入；
     * 预填默认值后仍为 null 的列显式插 NULL（数据库无默认值语义）。
     */
    public String batchInsert(List coll, ProviderContext context) {
        if (coll == null || coll.isEmpty()) {
            throw new SXException("批量添加集合为空");
        }
        TableInfo tableInfo = getTableInfo(context);
        for (Object item : coll) {
            EntityAutoFill.fillInsert(item, tableInfo);
        }
        String columns = Stream.of(tableInfo.getFields())
                .map(field -> TableQueryUtils.humpToLineName(field.getName()))
                .collect(Collectors.joining(","));
        String fieldsStr = Stream.of(tableInfo.getFields())
                .map(field -> TableQueryUtils.getInjectParameter(field, "item."))
                .collect(Collectors.joining(","));

        // 显式列名插入：不依赖表列序与实体字段序一致
        String sql = "INSERT INTO " + tableInfo.getTableName() + " (" + columns + ")"
                + " VALUES <foreach item='item' collection='coll' open='(' separator='),(' close=')'>"
                + fieldsStr + "</foreach>";
        log.debug(sql);
        return TableQueryUtils.getSqlScript(sql);
    }
}
