package top.zsmile.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import top.zsmile.common.datasource.annotation.DS;
import top.zsmile.common.datasource.properties.DataSourceProperties;
import top.zsmile.common.datasource.properties.DruidProperties;
import top.zsmile.common.datasource.properties.DynamicDataSourceProperties;
import top.zsmile.common.datasource.utils.DynamicDataSourceUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DynamicDataSourceConfig {

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

//    @Bean
//    @ConditionalOnMissingBean
//    public DataSourceProperties dataSourceProperties() {
//        return new DataSourceProperties();
//    }

    @ConditionalOnMissingBean
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();
        Map<Object, Object> dataSourceMap = getDynamicDataSource();
        dynamicDataSource.setMap(dataSourceMap);
        return dynamicDataSource;
    }

    @ConditionalOnMissingBean
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    private Map<Object, Object> getDynamicDataSource() {
        DruidProperties druid = dynamicDataSourceProperties.getDruid();
        Map<String, DataSourceProperties> dataSourcePropertiesMap = dynamicDataSourceProperties.getDatasource();
        Map<Object, Object> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size());
        dataSourcePropertiesMap.forEach((k, v) -> {
            DataSourceProperties mergeProperties = DynamicDataSourceUtils.merge(v, druid);
            DruidDataSource dataSource = DataSourceFactory.createDataSource(mergeProperties);
            dataSourceMap.put(k, dataSource);
        });
        return dataSourceMap;
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
