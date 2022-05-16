package top.zsmile.provider;

import com.google.common.base.Joiner;
import javafx.scene.control.Tab;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.apache.tomcat.util.buf.StringUtils;
import top.zsmile.meta.TableInfo;
import top.zsmile.utils.Constants;
import top.zsmile.utils.TableQueryUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class BaseSelectProvider extends BaseProvider {

    /**
     * 根据主键ID查询数据
     *
     * @param context
     * @return
     */
    public String selectById(ProviderContext context, @Param(Constants.COLUMNS) final String... columns) {
        TableInfo tableInfo = getTableInfo(context);

        String s = new SQL() {{
            SELECT(columns.length > 0 ? Stream.of(columns).map(TableQueryUtils::humpToLineName).toArray(String[]::new) : tableInfo.getColumns());
            FROM(tableInfo.getTableName());
            WHERE(tableInfo.primaryColumnWhere());
        }}.toString();
        System.out.println(s);
        return s;
    }


    /**
     * 根据主键ID列表查询数据
     *
     * @param context
     * @return
     */
    public String selectBatchIds(ProviderContext context, @Param(Constants.COLLECTION) final Collection<? extends Serializable> ids, @Param(Constants.COLUMNS) final String... columns) {
        TableInfo tableInfo = getTableInfo(context);

        String sql = new SQL() {{
            SELECT(columns.length > 0 ? Stream.of(columns).map(TableQueryUtils::humpToLineName).toArray(String[]::new) : tableInfo.getColumns());
            FROM(tableInfo.getTableName());
//            WHERE(tableInfo.getPrimaryColumn() + " in (" + Joiner.on(",").join(ids) + ")");
            WHERE(tableInfo.getPrimaryColumn() + " in <foreach item='item' collection='coll' open='(' separator=',' close=')'>#{item}</foreach>");
//            WHERE(tableInfo.getPrimaryColumn() + " in (" + TableQueryUtils.convertForeach("#{item}", "coll", null, "item", ",") + ")");
        }}.toString();

//        String str = "<script>SELECT %s FROM %s WHERE %s IN (%s) %s </script>";
//        String format = String.format(str, String.join(",", tableInfo.getColumns()), tableInfo.getTableName(),
//                tableInfo.getPrimaryColumn(), TableQueryUtils.convertForeach("#{item}", "coll", null, "item", ","), "");

//        return format;

        return TableQueryUtils.getSqlScript(sql);
    }


    /**
     * 根据字段集合查询，可传入字段名查询需要得字段
     *
     * @param context
     * @param columnMap
     * @param columns
     * @return
     */
    public String selectByMap(ProviderContext context, @Param(Constants.COLUMNS_MAP) Map<String, Object> columnMap, @Param(Constants.COLUMNS) final String... columns) {
        TableInfo tableInfo = getTableInfo(context);

        String s = new SQL() {{
            SELECT(columns.length > 0 ? Stream.of(columns).map(TableQueryUtils::humpToLineName).toArray(String[]::new) : tableInfo.getColumns());
            FROM(tableInfo.getTableName());
//            WHERE(tableInfo.getPrimaryColumn() + " in (" + Joiner.on(",").join(ids) + ")");
            WHERE(TableQueryUtils.getMapCondition(columnMap));
        }}.toString();

        return TableQueryUtils.getSqlScript(s);
    }


    /**
     * 根据对象entity查询不为null的数据，可传入字段名查询需要得字段
     *
     * @param context
     * @param entity
     * @param columns
     * @return
     */
    public String selectList(ProviderContext context, @Param(Constants.ENTITY) Object entity, @Param(Constants.COLUMNS) final String... columns) {
        TableInfo tableInfo = getTableInfo(context);


        String s = new SQL() {{
            SELECT(columns.length > 0 ? Stream.of(columns).map(TableQueryUtils::humpToLineName).toArray(String[]::new) : tableInfo.getColumns());
            FROM(tableInfo.getTableName());
//            WHERE(tableInfo.getPrimaryColumn() + " in (" + Joiner.on(",").join(ids) + ")");
            WHERE();
        }}.toString();

        return TableQueryUtils.getSqlScript(s);
    }


}
