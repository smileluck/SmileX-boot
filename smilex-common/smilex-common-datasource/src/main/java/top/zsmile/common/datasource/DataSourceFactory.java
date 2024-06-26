package top.zsmile.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import lombok.extern.slf4j.Slf4j;
import top.zsmile.common.core.utils.Asserts;
import top.zsmile.common.datasource.properties.DataSourceProperties;
import top.zsmile.common.datasource.properties.DruidProperties;
import top.zsmile.common.datasource.utils.DynamicDataSourceUtils;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
public class DataSourceFactory {
    public static DruidDataSource createDataSource(DataSourceProperties properties) throws SQLException {
        return createDataSource(null, properties, new DruidXADataSource());
    }

    public static DruidDataSource createDataSource(DruidProperties druidProperties, DataSourceProperties properties) throws SQLException {
        return createDataSource(druidProperties, properties, new DruidXADataSource());
    }

    public static DruidDataSource createDataSource(DruidProperties druidProperties, DataSourceProperties properties, DataSource dataSource) throws SQLException {
        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        if (druidProperties != null) {
            DynamicDataSourceUtils.merge(properties, druidProperties);
//            copyProperties(druidDataSource, druidProperties);
        }
        copyProperties(druidDataSource, properties);
        try {
            druidDataSource.init();
        } catch (SQLException e) {
            log.error("数据库初始话失败，error=>{}", e);
            throw e;
        }
        return druidDataSource;
    }

    private static void copyProperties(DruidDataSource druidDataSource, DruidProperties druidProperties) {
        Asserts.isNull(druidProperties, "DataSource Connection properties not null");
        try {
            druidDataSource.setInitialSize(druidProperties.getInitialSize());
            druidDataSource.setMaxActive(druidProperties.getMaxActive());
            druidDataSource.setMinIdle(druidProperties.getMinIdle());
            druidDataSource.setMaxWait(druidProperties.getMaxWait());
            druidDataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
            druidDataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
            druidDataSource.setMaxEvictableIdleTimeMillis(druidProperties.getMaxEvictableIdleTimeMillis());
            druidDataSource.setValidationQuery(druidProperties.getValidationQuery());
            druidDataSource.setValidationQueryTimeout(druidProperties.getValidationQueryTimeout());
            druidDataSource.setTestOnBorrow(druidProperties.getTestOnBorrow());
            druidDataSource.setTestOnReturn(druidProperties.getTestOnReturn());
            druidDataSource.setTestWhileIdle(druidProperties.getTestWhileIdle());
            druidDataSource.setPoolPreparedStatements(druidProperties.getPoolPreparedStatements());
            druidDataSource.setMaxOpenPreparedStatements(druidProperties.getMaxOpenPreparedStatements());
            druidDataSource.setSharePreparedStatements(druidProperties.getSharePreparedStatements());
            druidDataSource.setFilters(druidProperties.getFilters());
            druidDataSource.setConnectionProperties(druidProperties.getConnectionProperties());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void copyProperties(DruidDataSource druidDataSource, DataSourceProperties dsProperties) {
        Asserts.isNull(dsProperties, "DataSource Connection properties not null");
        druidDataSource.setDriverClassName(dsProperties.getDriverClassName());
        druidDataSource.setUrl(dsProperties.getUrl());
        druidDataSource.setUsername(dsProperties.getUsername());
        druidDataSource.setPassword(dsProperties.getPassword());
        druidDataSource.setInitialSize(dsProperties.getInitialSize());
        druidDataSource.setMaxActive(dsProperties.getMaxActive());
        druidDataSource.setMinIdle(dsProperties.getMinIdle());
        druidDataSource.setMaxWait(dsProperties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(dsProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(dsProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setMaxEvictableIdleTimeMillis(dsProperties.getMaxEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(dsProperties.getValidationQuery());
        druidDataSource.setValidationQueryTimeout(dsProperties.getValidationQueryTimeout());
        druidDataSource.setTestOnBorrow(dsProperties.getTestOnBorrow());
        druidDataSource.setTestOnReturn(dsProperties.getTestOnReturn());
        druidDataSource.setTestWhileIdle(dsProperties.getTestWhileIdle());
        druidDataSource.setPoolPreparedStatements(dsProperties.getPoolPreparedStatements());
        druidDataSource.setMaxOpenPreparedStatements(dsProperties.getMaxOpenPreparedStatements());
        druidDataSource.setSharePreparedStatements(dsProperties.getSharePreparedStatements());
        try {
            druidDataSource.setFilters(dsProperties.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        druidDataSource.setConnectionProperties(dsProperties.getConnectionProperties());
    }

}
