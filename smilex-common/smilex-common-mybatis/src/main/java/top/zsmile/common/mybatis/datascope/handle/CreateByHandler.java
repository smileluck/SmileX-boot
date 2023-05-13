package top.zsmile.common.mybatis.datascope.handle;

import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import top.zsmile.common.mybatis.datascope.DataScopeContentHolder;
import top.zsmile.common.mybatis.datascope.DataScopeFactory;
import top.zsmile.common.mybatis.datascope.DataScopeHandleFactory;
import top.zsmile.common.mybatis.datascope.DataScopePerm;

import java.util.Arrays;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Version: 1.0.0
 * @Author: Mapistrator
 * @Date: 2023/03/06/10:47
 * @ClassName: CreateByHandler
 * @Description: CreateByHandler
 */
@Slf4j
@Component
public class CreateByHandler implements AbstractDataScopeHandler, InitializingBean {


    @Override
    public String handle(MappedStatement ms, BoundSql boundSql, DataScopePerm dataScopePerm, Map admin) {
        SqlCommandType sqlCommandType = ms.getSqlCommandType();

        if (dataScopePerm.getOpQuery() && sqlCommandType == SqlCommandType.SELECT) {
//            String concatSql = inCondition(admin);
            String s = addWhere(dataScopePerm, boundSql.getSql(), admin);
            log.info("select => {}", s);
//            try {
//                Field sql = boundSql.getClass().getDeclaredField("sql");
//                sql.setAccessible(true);
//                sql.set(boundSql, s);
//                Field msBoundSql = ms.getClass().getDeclaredField("boundSql");
//                msBoundSql.setAccessible(true);
//                msBoundSql.set(ms, boundSql);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
            return s;
        } else if (dataScopePerm.getOpUpdate()) {
            // TODO 更新和删除SQL先不处理
            if (sqlCommandType == SqlCommandType.UPDATE || sqlCommandType == SqlCommandType.DELETE) {
            }
        }
        return boundSql.getSql();
    }

    private String addWhere(DataScopePerm dataScopePerm, String sql, Map admin) {
        try {
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            SelectBody selectBody = select.getSelectBody();
            if (selectBody instanceof PlainSelect) {
                handlePlainSelect(dataScopePerm, admin, selectBody);
                return select.toString();
            } else if (selectBody instanceof SetOperationList) {
                SetOperationList setOperationList = (SetOperationList) selectBody;
                addWhere(dataScopePerm, setOperationList.getSelects(), admin);
                return select.toString();
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return sql;
    }

    private void addWhere(DataScopePerm dataScopePerm, List<SelectBody> selects, Map admin) {
        selects.stream().forEach(selectBody -> {
            if (selectBody instanceof PlainSelect) {
                try {
                    handlePlainSelect(dataScopePerm, admin, selectBody);
                } catch (JSQLParserException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 处理PlainSelect类型
     */
    private void handlePlainSelect(DataScopePerm dataScopePerm, Map admin, SelectBody selectBody) throws JSQLParserException {
        PlainSelect plainSelect = (PlainSelect) selectBody;
        Table fromTable = (Table) plainSelect.getFromItem();
        String fromTableName = ((Table) plainSelect.getFromItem()).getName().trim();
        if (StringUtils.isNotBlank(fromTableName)) {
            String fromTableAlias = fromTable.getAlias() != null ? fromTable.getAlias().getName() : Strings.EMPTY;
            if (dataScopePerm.getFilterTable().length > 0) {
                List<String> tablesNames = Arrays.asList(dataScopePerm.getFilterTable());
                if (tablesNames.contains(fromTableName)) {
                    String createBySql = StringUtils.isNotBlank(fromTableAlias) ? fromTableAlias + ".create_by = " + admin.get("username") : "create_by = " + admin.get("username");
                    AndExpression andExpression = new AndExpression(plainSelect.getWhere(), CCJSqlParserUtil.parseCondExpression(createBySql));
                    plainSelect.setWhere(andExpression);
                }
            }
        }
    }

    private String inCondition(Map admin) {
        DataScopeContentHolder.add(DataScopeFactory.create());
        String sql = null;
        try {
            CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
//                List<Goods> list = goodsService.lambdaQuery().select(Goods::getId).eq(Goods::getCreateBy, admin.getUserName()).list();
//                if (!CollectionUtils.isEmpty(list)) {
//                    Collector<CharSequence, ?, String> joining = Collectors.joining(",", "id in (", ")");
//                    return list.stream().map(item -> item.getId().toString()).collect(joining);
//                }
                return null;
            });
            sql = stringCompletableFuture.join();
        } catch (Exception ex) {
            DataScopeContentHolder.poll();
            log.error("生成条件失败 => {}", ex);
        }
        return sql;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataScopeHandleFactory.set(AbstractDataScopeHandler.CREATE_BY, this);
    }
}
