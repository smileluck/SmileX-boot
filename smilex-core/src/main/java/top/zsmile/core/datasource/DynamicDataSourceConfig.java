package top.zsmile.core.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import top.zsmile.core.datasource.properties.DataSourceProperties;

import javax.sql.DataSource;

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

        DataSource dataSource = DataSourceFactory.createDataSource(dataSourceProperties);
        dynamicDataSource.addDataSource(MASTER, dataSource);
        dynamicDataSource.setDefaultTargetDataSource(dataSource);
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
