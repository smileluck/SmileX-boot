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
        // 当前数据源Key
        this.identification = DataSourceContentHolder.get();
        this.dataSource = dataSource;
        connections = new ConcurrentHashMap<>();
        log.debug("init dynamic transaction, identify key => {},address => {}", this.identification, this);
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        /* 获取当前生效的数据源标识 */
        String current = DataSourceContentHolder.get();
        log.debug("current key => {}, identify key=> {}", current, this.identification);
        // 如果当前数据源是主数据源
        if (current.equals(this.identification)) {
            // 如果为空则创建连接
            if (this.connection == null) {
                openConnection();
            }
            return this.connection;
        } else {
            /* 不是默认数据源，获取连接并设置属性 */
            if (!connections.containsKey(current)) {
                // 如果连接不包含该数据源KEY获取的连接
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

    /**
     * 打开连接
     *
     * @throws SQLException
     */
//    private void openConnection() throws SQLException {
//        // 获取连接
//        this.connection = DataSourceUtils.getConnection(this.dataSource);
//        // 是否自动提交
//        this.autoCommit = this.getConnection().getAutoCommit();
//        // 确定当前连接是否是事务性的。
//        // 即通过 Spring 的事务管理器绑定到当前线程的
//        this.isConnectionTransactional =
//                DataSourceUtils.isConnectionTransactional(this.connection, this.dataSource);
//
//        // 数据源是否是DynamicDataSource
//        if (this.dataSource instanceof DynamicDataSource) {
//            // 获取主数据源的连接
//            this.connection = DataSourceUtils.getConnection((DataSource) ((DynamicDataSource) dataSource).get(this.identification));
//            // 设置自动提交
//            this.connection.setAutoCommit(this.autoCommit);
//        }
//    }

    private void openConnection() throws SQLException {

        // 获取连接
        this.connection = DataSourceUtils.getConnection(this.dataSource);
        // 是否自动提交
        this.autoCommit = this.getConnection().getAutoCommit();
        // 确定当前连接是否是事务性的。
        // 即通过 Spring 的事务管理器绑定到当前线程的
        this.isConnectionTransactional =
                DataSourceUtils.isConnectionTransactional(this.connection, this.dataSource);
        log.debug("jdbc connection [{}] will {} be managed by spring", this.connection, (this.isConnectionTransactional ? "" : "not"));
//
//
//        DataSource dataSource = this.dataSource;
//
//        // 数据源是否是DynamicDataSource
//        if (this.dataSource instanceof DynamicDataSource) {
//            dataSource = (DataSource) ((DynamicDataSource) dataSource).get(this.identification);
//            // 获取主数据源的连接
//            this.connection = DataSourceUtils.getConnection(dataSource);
//        } else {
//            // 获取连接
//            this.connection = DataSourceUtils.getConnection(dataSource);
//        }
//        // 是否自动提交
//        this.autoCommit = this.getConnection().getAutoCommit();
////            // 设置自动提交
//        this.connection.setAutoCommit(this.autoCommit);
//        // 确定当前连接是否是事务性的。
//        // 即通过 Spring 的事务管理器绑定到当前线程的
//        this.isConnectionTransactional =
//                DataSourceUtils.isConnectionTransactional(this.connection, dataSource);
    }

    /**
     * 提交事务
     *
     * @throws SQLException
     */
    @Override
    public void commit() throws SQLException {
        // 如果主事务不为空 && 如果是 Spring 管理的事务性连接 && 不是自动提交
        if (this.connection != null && this.isConnectionTransactional &&
                !this.autoCommit) {
            // 提交主连接的事务
            log.debug("committing jdbc connection [{}]", this.connection);
            this.connection.commit();

            // 遍历提交子连接的事务
            for (Connection conn : connections.values()) {
                conn.commit();
            }
        }
    }

    /**
     * 回滚事务
     *
     * @throws SQLException
     */
    @Override
    public void rollback() throws SQLException {
        if (this.connection != null && this.isConnectionTransactional &&
                !this.autoCommit) {
            log.debug("rolling back jdbc connection [{}]", this.connection);
            this.connection.rollback();
            for (Connection conn : connections.values()) {
                conn.rollback();
            }
        }
    }

    /**
     * 关闭并释放连接
     *
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
        log.debug("close jdbc connection");
        DataSourceUtils.releaseConnection(this.connection, this.dataSource);
        for (Connection conn : connections.values()) {
            DataSourceUtils.releaseConnection(conn, this.dataSource);
        }
    }

    /**
     * 获取事务超时时间
     *
     * @return
     * @throws SQLException
     */
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
