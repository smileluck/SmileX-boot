package top.zsmile.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import top.zsmile.common.datasource.properties.DataSourceProperties;

import java.sql.SQLException;

@Configuration
@Order(-1)
public class DynamicDataSourceConfig {

    public final static String MASTER = "master";

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource(DataSourceProperties dataSourceProperties) {
        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();

        DruidDataSource dataSource = DataSourceFactory.createDataSource(dataSourceProperties);
        dynamicDataSource.addDataSource(MASTER, dataSource);
        dynamicDataSource.setDefaultTargetDataSource(dataSource);
        try {
            dataSource.init();
        } catch (SQLException throwables) {
            throw new RuntimeException(String.format("数据库[%s]初始化异常", MASTER));
//            SpringContextUtils.close();
        }

        return dynamicDataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }


//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
//            throws Exception {
//        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
//
//        GlobalConfig globalConfig = new GlobalConfig();
//        globalConfig.setBanner(false);
//
//        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
//        dbConfig.setIdType(IdType.ASSIGN_ID);
//        dbConfig.setLogicDeleteField("del_flag");
//        dbConfig.setLogicDeleteValue("1");
//        dbConfig.setLogicNotDeleteValue("0");
//        dbConfig.setTableUnderline(true);
//        globalConfig.setDbConfig(dbConfig);
//
//
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
//        configuration.setCallSettersOnNulls(true);
//
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/**/*.xml"));
//        sessionFactory.setGlobalConfig(globalConfig);
//        sessionFactory.setDataSource(dynamicDataSource);
//        sessionFactory.setConfiguration(configuration);
//        return sessionFactory.getObject();
//    }
}
