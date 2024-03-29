package top.zsmile.common.datascope.handle;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.zsmile.common.datascope.DataScopeHandleFactory;
import top.zsmile.common.datascope.DataScopePerm;

/**
 * 根据创建用户拦截
 *
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/03/06/10:47
 * @ClassName: CreateByHandler
 * @Description: CreateByHandler
 */
@Slf4j
@Component
public class CreateByHandle extends AbstractDataScopeHandle implements InitializingBean {

//    @Resource
//    private CommonAuthApi commonAuthApi;

    @Override
    public String handle(MappedStatement ms, BoundSql boundSql, DataScopePerm dataScopePerm) {
        SqlCommandType sqlCommandType = ms.getSqlCommandType();

        if (dataScopePerm.getOpQuery() && sqlCommandType == SqlCommandType.SELECT) {
//            String concatSql = inCondition(admin);
//            String s = addWhere(dataScopePerm, boundSql.getSql(), admin);
//            log.info("select => {}", s);
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
//            return s;
        } else if (dataScopePerm.getOpUpdate()) {
            // TODO 更新和删除SQL先不处理
            if (sqlCommandType == SqlCommandType.UPDATE || sqlCommandType == SqlCommandType.DELETE) {
            }
        }
        return boundSql.getSql();
    }

//    private String addWhere(DataScopePerm dataScopePerm, String sql, Admin admin) {
//        try {
//            Select select = (Select) CCJSqlParserUtil.parse(sql);
//            SelectBody selectBody = select.getSelectBody();
//            if (selectBody instanceof PlainSelect) {
//                handlePlainSelect(dataScopePerm, admin, selectBody);
//                return select.toString();
//            } else if (selectBody instanceof SetOperationList) {
//                SetOperationList setOperationList = (SetOperationList) selectBody;
//                addWhere(dataScopePerm, setOperationList.getSelects(), admin);
//                return select.toString();
//            }
//        } catch (JSQLParserException e) {
//            e.printStackTrace();
//        }
//        return sql;
//    }

//    private void addWhere(DataScopePerm dataScopePerm, List<SelectBody> selects, Admin admin) {
//        selects.stream().forEach(selectBody -> {
//            if (selectBody instanceof PlainSelect) {
//                handlePlainSelect(dataScopePerm, admin, selectBody);
//            }
//        });
//    }

    /**
     * 处理PlainSelect类型
     */
//    private void handlePlainSelect(DataScopePerm dataScopePerm, Admin admin, SelectBody selectBody) {
//        try {
//            PlainSelect plainSelect = (PlainSelect) selectBody;
//            Table fromTable = (Table) plainSelect.getFromItem();
//            String fromTableName = ((Table) plainSelect.getFromItem()).getName().trim();
//            if (StringUtils.isNotBlank(fromTableName)) {
//                String fromTableAlias = fromTable.getAlias() != null ? fromTable.getAlias().getName() : Strings.EMPTY;
//                if (dataScopePerm.getFilterTable() != null && dataScopePerm.getFilterTable().length > 0) {
//                    List<String> filterTables = Arrays.asList(dataScopePerm.getFilterTable());
//                    if (filterTables.contains(fromTableName)) {
//                        String createBySql = StringUtils.isNotBlank(fromTableAlias) ? fromTableAlias + ".create_by = " + admin.getUserName() : "create_by = " + admin.getUserName();
//                        AndExpression andExpression = new AndExpression(plainSelect.getWhere(), CCJSqlParserUtil.parseCondExpression(createBySql));
//                        plainSelect.setWhere(andExpression);
//                    }
//                }
//            }
//        } catch (JSQLParserException e) {
//            e.printStackTrace();
//        }
//    }

//    private String inCondition(Admin admin) {
//        DataScopeContentHolder.add(DataScopeFactory.create());
//        String sql = null;
//        try {
//            CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
//                List<Goods> list = goodsService.lambdaQuery().select(Goods::getId).eq(Goods::getCreateBy, admin.getUserName()).list();
//                if (!CollectionUtils.isEmpty(list)) {
//                    Collector<CharSequence, ?, String> joining = Collectors.joining(",", "id in (", ")");
//                    return list.stream().map(item -> item.getId().toString()).collect(joining);
//                }
//                return null;
//            });
//            sql = stringCompletableFuture.join();
//        } catch (Exception ex) {
//            DataScopeContentHolder.poll();
//            log.error("生成条件失败 => {}", ex);
//        }
//        return sql;
//    }
    @Override
    public void afterPropertiesSet() throws Exception {
        DataScopeHandleFactory.set(AbstractDataScopeHandle.CREATE_BY, this);
    }

}
