package top.zsmile.common.datasource.utils;

import top.zsmile.common.datasource.properties.DataSourceProperties;
import top.zsmile.common.datasource.properties.DruidProperties;

public class DynamicDataSourceUtils {

    public static DataSourceProperties merge(DataSourceProperties dataSourceProperties, DruidProperties druidProperties) {
        if (druidProperties != null) {
            dataSourceProperties.setInitialSize(druidProperties.getInitialSize());
            dataSourceProperties.setMaxActive(druidProperties.getMaxActive());
            dataSourceProperties.setMinIdle(druidProperties.getMinIdle());
            dataSourceProperties.setMaxWait(druidProperties.getMaxWait());
            dataSourceProperties.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
            dataSourceProperties.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
            dataSourceProperties.setMaxEvictableIdleTimeMillis(druidProperties.getMaxEvictableIdleTimeMillis());
            dataSourceProperties.setValidationQuery(druidProperties.getValidationQuery());
            dataSourceProperties.setValidationQueryTimeout(druidProperties.getValidationQueryTimeout());
            dataSourceProperties.setTestOnBorrow(druidProperties.getTestOnBorrow());
            dataSourceProperties.setTestOnReturn(druidProperties.getTestOnReturn());
            dataSourceProperties.setTestWhileIdle(druidProperties.getTestWhileIdle());
            dataSourceProperties.setPoolPreparedStatements(druidProperties.getPoolPreparedStatements());
            dataSourceProperties.setMaxOpenPreparedStatements(druidProperties.getMaxOpenPreparedStatements());
            dataSourceProperties.setSharePreparedStatements(druidProperties.getSharePreparedStatements());
            dataSourceProperties.setFilters(druidProperties.getFilters());
            dataSourceProperties.setConnectionProperties(druidProperties.getConnectionProperties());
        }
        return dataSourceProperties;
    }
}
