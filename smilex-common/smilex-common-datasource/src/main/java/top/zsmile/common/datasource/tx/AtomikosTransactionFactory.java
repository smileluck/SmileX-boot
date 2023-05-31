package top.zsmile.common.datasource.tx;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import top.zsmile.common.datasource.DynamicDataSource;

import javax.sql.DataSource;

public class AtomikosTransactionFactory extends SpringManagedTransactionFactory {
    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {

        DataSource ds = dataSource;
        if (dataSource instanceof DynamicDataSource) {
            DynamicDataSource dynamicDataSource = (DynamicDataSource) dataSource;
            ds = (DataSource) dynamicDataSource.getCurrent();
        }
        return new SpringManagedTransaction(ds);
    }
}
