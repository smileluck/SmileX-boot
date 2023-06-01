package top.zsmile.common.datasource.tx;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;

public class DynamicDataSourceTransactionFactory extends SpringManagedTransactionFactory {
    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
//        DataSource currentDs = dataSource;
//        if (dataSource instanceof DynamicDataSource) {
//            currentDs = (DataSource) DynamicDataSource.getInstance().getCurrent();
//        }
        return new DynamicDataSourceTransaction(dataSource);
    }
}
