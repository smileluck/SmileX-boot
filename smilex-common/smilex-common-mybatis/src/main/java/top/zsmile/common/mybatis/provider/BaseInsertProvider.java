package top.zsmile.common.mybatis.provider;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;
import top.zsmile.api.system.common.CommonAuthApi;
import top.zsmile.common.core.utils.NameStyleUtils;
import top.zsmile.common.core.utils.SnowFlake;
import top.zsmile.core.exception.SXException;
import top.zsmile.core.utils.SpringContextUtils;
import top.zsmile.common.mybatis.meta.TableInfo;
import top.zsmile.common.mybatis.utils.ReflectUtils;
import top.zsmile.common.mybatis.utils.TableQueryUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class BaseInsertProvider extends BaseProvider {

    private SnowFlake snowFlake = new SnowFlake(0, 0);

    public SnowFlake getSnowFlake() {
        return snowFlake;
    }

    /**
     * 插入单条数据
     *
     * @param entity
     * @param context
     * @return
     */
    public String insert(Object entity, ProviderContext context) {
        TableInfo tableInfo = getTableInfo(context);
        Field[] fields = tableInfo.getFields();

        setDefaultIdValue(entity, tableInfo);
        setTenantIdValue(entity, tableInfo);

        return new SQL() {{
            INSERT_INTO(tableInfo.getTableName());
            INTO_COLUMNS(Stream.of(fields).filter(field -> !StringUtils.isEmpty(ReflectUtils.getFieldValue(entity, field))).map(TableQueryUtils::humpToLineName).toArray(String[]::new));
            INTO_VALUES(Stream.of(fields).filter(field -> !StringUtils.isEmpty(ReflectUtils.getFieldValue(entity, field))).map(TableQueryUtils::getInjectParameter).toArray(String[]::new));
        }}.toString();
    }

    /**
     * 批量插入数据
     *
     * @param list
     * @param context
     * @return
     */
    public String batchInsert(List list, ProviderContext context) {
        if (list.size() == 0) {
            throw new SXException("批量添加集合为空");
        }
        TableInfo tableInfo = getTableInfo(context);
        Field[] fields = tableInfo.getFields();

        String sql = new SQL() {{
            INSERT_INTO(tableInfo.getTableName());
            INTO_COLUMNS();
            // 方式一：直接拼接
//            for (int i = 0; i < list.size(); i++) {
//                Object entity = list.get(i);
//                if (i != 0) {
//                    ADD_ROW();
//                }
//                setDefaultIdValue(entity, tableInfo);
//                INTO_VALUES(Stream.of(fields).map(field -> {
//                            System.out.println(ReflectUtils.getFieldValue(entity, field).toString());
//                            return "'" + getValue(entity, field) + "'";
//                        }
//                ).toArray(String[]::new));
//            }
        }}.toString();

        // 方式二：使用注入的方式
        for (int i = 0; i < list.size(); i++) {
            setDefaultIdValue(list.get(i), tableInfo);
            setTenantIdValue(list.get(i), tableInfo);
        }
        String[] strings = Stream.of(fields).map(item ->
                TableQueryUtils.getInjectParameter(item, "item.")).toArray(String[]::new);

        sql += " VALUES <foreach item='item' collection='coll' open='(' separator='),(' close=')'>" +
                Joiner.on(",").join(strings) +
                "</foreach>";

        log.debug(sql);
        return TableQueryUtils.getSqlScript(sql);
    }

    /**
     * 获取值并处理后插入数据库
     *
     * @param entity
     * @param field
     */
    private String getValue(Object entity, Field field) {
        Object fieldValue = ReflectUtils.getFieldValue(entity, field);
        if (field.getType() == Date.class) {
            fieldValue = DateFormatUtils.format((Date) fieldValue, "yyyy-MM-dd HH:mm:ss");
        }
        return "'" + fieldValue.toString() + "'";
    }

    /**
     * 设置雪花ID
     *
     * @param t
     */
    private void setDefaultIdValue(Object t, TableInfo tableInfo) {
        try {
            Field field = FieldUtils.getField(t.getClass(), tableInfo.getPrimaryColumn(), true);
            if (null != field) {
                Object id = FieldUtils.readField(t, tableInfo.getPrimaryColumn(), true);
                if (null == id || "0".equals(String.valueOf(id))) {
                    FieldUtils.writeField(t, tableInfo.getPrimaryColumn(), this.getSnowFlake().nextId(), true);
                }
            }

        } catch (IllegalAccessException var4) {
            throw new IllegalArgumentException(var4.getMessage());
        }
    }

    /**
     * 设置租户ID
     */
    private void setTenantIdValue(Object t, TableInfo tableInfo) {

        try {
            if (tableInfo.hasTenantColumn()) {
                String tenantColumn = NameStyleUtils.lineToHump(tableInfo.getTenantColumn(), false);
                Field field = FieldUtils.getField(t.getClass(), tenantColumn, true);
                if (null != field) {
                    Object id = FieldUtils.readField(t, tenantColumn, true);
                    if (null == id || "0".equals(String.valueOf(id))) {
                        CommonAuthApi commonAuthApi = SpringContextUtils.getBean(CommonAuthApi.class);
                        FieldUtils.writeField(t, tenantColumn, commonAuthApi.queryTenantId(), true);
                    }
                }
            }
        } catch (IllegalAccessException var4) {
            throw new IllegalArgumentException(var4.getMessage());
        }
    }
}
