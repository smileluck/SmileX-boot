package top.zsmile.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.BeanUtils;
import top.zsmile.common.datasource.properties.DataSourceProperties;

import javax.sql.DataSource;

public class DataSourceFactory {
    public static DruidDataSource createDataSource(DataSourceProperties properties) {
        return createDataSource(new DruidDataSource(), properties);
    }

    public static DruidDataSource createDataSource(DataSource dataSource, DataSourceProperties properties) {
        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        BeanUtils.copyProperties(properties, druidDataSource);
        return druidDataSource;
    }

}
