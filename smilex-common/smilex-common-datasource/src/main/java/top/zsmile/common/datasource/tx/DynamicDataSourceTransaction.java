package top.zsmile.common.datasource.tx;

import org.apache.ibatis.transaction.Transaction;
import org.springframework.jdbc.datasource.DataSourceUtils;
import top.zsmile.common.datasource.ds.DynamicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicDataSourceTransaction implements Transaction {
    private final DynamicDataSource dynamicDataSource;
    private ConcurrentHashMap<String, DataSource> dataSources;
    private ConcurrentHashMap<String, Connection> connections;
    private ConcurrentHashMap<String, Boolean> autoCommits;
    private ConcurrentHashMap<String, Boolean> isConnectionTransactionals;

    public DynamicDataSourceTransaction(DataSource dataSource) {
        this.dynamicDataSource = (DynamicDataSource) dataSource;
        dataSources = new ConcurrentHashMap<>();
        connections = new ConcurrentHashMap<>();
        autoCommits = new ConcurrentHashMap<>();
        isConnectionTransactionals = new ConcurrentHashMap<>();
    }

    public Connection getConnection() throws SQLException {
        String dataBaseID = DynamicDataSource.getInstance().getKey();
        if (!dataSources.containsKey(dataBaseID)) {
            DataSource dataSource = (DataSource) dynamicDataSource.get();
            dataSources.put(dataBaseID, dataSource);
        }
        if (!connections.containsKey(dataBaseID)) {
            Connection connection = DataSourceUtils.getConnection(dataSources.get(dataBaseID));
            connections.put(dataBaseID, connection);
        }
        if (!autoCommits.containsKey(dataBaseID)) {
            boolean autoCommit = connections.get(dataBaseID).getAutoCommit();
            autoCommits.put(dataBaseID, autoCommit);
        }
        if (!isConnectionTransactionals.containsKey(dataBaseID)) {
            boolean isConnectionTransactional = DataSourceUtils.isConnectionTransactional(connections.get(dataBaseID), dataSources.get(dataBaseID));
            isConnectionTransactionals.put(dataBaseID, isConnectionTransactional);
        }
        return connections.get(dataBaseID);
    }


    public void commit() throws SQLException {
        for (String dataBaseID : connections.keySet()) {
            Connection connection = connections.get(dataBaseID);
            boolean isConnectionTransactional = isConnectionTransactionals.get(dataBaseID);
            boolean autoCommit = autoCommits.get(dataBaseID);
            if (connection != null && !isConnectionTransactional && !autoCommit) {
                connection.commit();
            }
        }
    }

    public void rollback() throws SQLException {
        for (String dataBaseID : connections.keySet()) {
            Connection connection = connections.get(dataBaseID);
            boolean isConnectionTransactional = isConnectionTransactionals.get(dataBaseID);
            boolean autoCommit = autoCommits.get(dataBaseID);
            if (connection != null && !isConnectionTransactional && !autoCommit) {
                connection.rollback();
            }
        }
    }

    public void close() {
        for (String dataBaseID : connections.keySet()) {
            Connection connection = connections.get(dataBaseID);
            DataSource dataSource = dataSources.get(dataBaseID);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public Integer getTimeout() {
        return null;
    }
}
