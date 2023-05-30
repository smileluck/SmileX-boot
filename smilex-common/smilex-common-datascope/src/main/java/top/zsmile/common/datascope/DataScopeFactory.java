package top.zsmile.common.datascope;

import top.zsmile.common.datascope.annotation.DataScope;
import top.zsmile.common.datascope.handle.AbstractDataScopeHandle;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/02/24/15:15
 * @ClassName: DataScopeFactory
 * @Description: DataScopeFactory
 */
public class DataScopeFactory {

    // TODO 可以优化默认配置为参数
    private static final DataScopePerm INSTANCE;

    static {
        INSTANCE = create(null, null, false, false, false);
    }

    public static DataScopePerm create(String fieldName, String[] filterTable, boolean needFilter, boolean opUpdate, boolean opQuery) {
        return create(fieldName, filterTable, needFilter, opUpdate, opQuery, AbstractDataScopeHandle.NIL);
    }

    public static DataScopePerm create(String fieldName, String[] filterTable, boolean needFilter, boolean opUpdate, boolean opQuery, String handlerKey) {
        DataScopePerm dataScopePerm = new DataScopePerm();
        dataScopePerm.setFieldName(fieldName);
        dataScopePerm.setFilterTable(filterTable);
        dataScopePerm.setNeedFilter(needFilter);
        dataScopePerm.setOpQuery(opUpdate);
        dataScopePerm.setOpUpdate(opQuery);
        dataScopePerm.setHandleKey(handlerKey);
        return dataScopePerm;
    }

    public static DataScopePerm create(DataScope dataScope) {
        return create(dataScope.fieldName(), dataScope.filterTable(), dataScope.needFilter(), dataScope.opUpdate(), dataScope.opQuery(), dataScope.handleKey());
    }

    /**
     * 默认的
     *
     * @return
     */
    public static DataScopePerm create() {
        return INSTANCE;
    }
}
