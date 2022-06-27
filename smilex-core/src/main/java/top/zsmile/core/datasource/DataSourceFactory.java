package top.zsmile.core.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper;
import org.springframework.beans.BeanUtils;
import top.zsmile.core.datasource.properties.DataSourceProperties;

import javax.sql.DataSource;
import java.sql.SQLException;

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
