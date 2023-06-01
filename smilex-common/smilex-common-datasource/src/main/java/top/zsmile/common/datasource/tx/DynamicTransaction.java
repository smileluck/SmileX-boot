package top.zsmile.common.datasource.tx;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import top.zsmile.common.datasource.DataSourceContentHolder;
import top.zsmile.common.datasource.ds.DynamicDataSource;

import javax.sql.DataSource;
import javax.transaction.xa.XAResource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class DynamicTransaction implements Transaction {
    /**
     * 数据源
     */
    private final DataSource dataSource;
    /**
     * 主数据源连接
     */
    private Connection connection;
    /**
     * 是否开启事务
     */
    private Boolean isConnectionTransactional;
    /**
     * 是否自动提交
     */
    private Boolean autoCommit;
    /**
     * 连接标识
     */
    private String identification;

    /**
     * 其他连接缓存
     */
    private ConcurrentMap<String, Connection> connections;

    /**
     * 构造器
     *
     * @param dataSource 数据源
     */
    public DynamicTransaction(DataSource dataSource) {
        Assert.notNull(dataSource, "No DataSource specified");
        this.identification = DataSourceContentHolder.get();
        this.dataSource = dataSource;
        connections = new ConcurrentHashMap<>();
    }

    @Override
    public Connection getConnection() throws SQLException {
        /* 获取当前生效的数据源标识 */
        String current = DataSourceContentHolder.get();
        if (current.equals(this.identification)) {
            if (this.connection == null) {
                openConnection();
            }
            return this.connection;
        } else {
            /* 不是默认数据源，获取连接并设置属性 */
            if (!connections.containsKey(current)) {
                try {
                    Connection conn = this.dataSource.getConnection();
                    /* 自动提交属性和主数据源保持连接 */
                    conn.setAutoCommit(this.autoCommit);
                    connections.put(current, conn);
                } catch (SQLException ex) {
                    throw new CannotGetJdbcConnectionException("could not get jdbc connection", ex);
                }
            }
            return connections.get(current);
        }
    }

    private void openConnection() throws SQLException {
        this.connection = DataSourceUtils.getConnection(this.dataSource);
        this.autoCommit = this.getConnection().getAutoCommit();
        this.isConnectionTransactional =
                DataSourceUtils.isConnectionTransactional(this.connection, this.dataSource);
        if (this.dataSource instanceof DynamicDataSource) {
            this.connection = DataSourceUtils.getConnection((DataSource) ((DynamicDataSource) dataSource).get(this.identification));
            this.connection.setAutoCommit(this.autoCommit);
        }
        if (log.isDebugEnabled()) {
            log.debug("jdbc connection [{}] will {} be managed by spring",
                    this.connection, (this.isConnectionTransactional ? " " : "not"));
        }
    }

    @Override
    public void commit() throws SQLException {
        if (this.connection != null && this.isConnectionTransactional &&
                !this.autoCommit) {
            if (log.isDebugEnabled()) {
                log.debug("committing jdbc connection [{}]", this.connection);
            }
            this.connection.commit();
            for (Connection conn : connections.values()) {
                conn.commit();
            }
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (this.connection != null && this.isConnectionTransactional &&
                !this.autoCommit) {
            if (log.isDebugEnabled()) {
                log.debug("rolling back jdbc connection [{}]", this.connection);
            }
            this.connection.rollback();
            for (Connection conn : connections.values()) {
                conn.rollback();
            }
        }
    }

    @Override
    public void close() throws SQLException {
        DataSourceUtils.releaseConnection(this.connection, this.dataSource);
        for (Connection conn : connections.values()) {
            DataSourceUtils.releaseConnection(conn, this.dataSource);
        }
    }

    @Override
    public Integer getTimeout() throws SQLException {
        ConnectionHolder holder = (ConnectionHolder)
                TransactionSynchronizationManager.getResource(dataSource);
        if (holder != null && holder.hasTimeout()) {
            return holder.getTimeToLiveInSeconds();
        }
        return null;
    }
}
