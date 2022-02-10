package top.zsmile.core.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper;
import org.springframework.beans.BeanUtils;
import top.zsmile.core.datasource.properties.DataSourceProperties;

public class DataSourceFactory {


    public static boolean createDataSource(DataSourceProperties properties) {
        DruidDataSource dataSource = new DruidDataSource();
        BeanUtils.copyProperties(properties, dataSource);
        System.out.println(dataSource);
        return true;
    }

    public static void main(String[] args) {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        createDataSource(dataSourceProperties);
    }
}
