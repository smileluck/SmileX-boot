package top.zsmile.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.SneakyThrows;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SqlSessionFactoryBeanCustomizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import top.zsmile.common.datasource.ds.DynamicDataSource;
import top.zsmile.common.datasource.properties.DataSourceProperties;
import top.zsmile.common.datasource.properties.DruidProperties;
import top.zsmile.common.datasource.properties.DynamicDataSourceProperties;
import top.zsmile.common.datasource.tx.DynamicTransactionFactory;
import top.zsmile.common.datasource.utils.DynamicDataSourceUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DynamicDataSourceConfig {

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    /**
     * 动态数据源
     *
     * @return
     */
    @ConditionalOnMissingBean
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();
        Map<Object, Object> dataSourceMap = getDynamicDataSource();
        dynamicDataSource.setMap(dataSourceMap);
        return dynamicDataSource;
    }

    /**
     * mybatis-spring start config sql-session-factory
     *
     * @param dataSource
     * @return
     */
    @Bean
    public SqlSessionFactoryBeanCustomizer sqlSessionFactoryBeanCustomizer(@Qualifier("dynamicDataSource") DynamicDataSource dataSource) {
        return new SqlSessionFactoryBeanCustomizer() {
            @Override
            public void customize(SqlSessionFactoryBean factoryBean) {
                // customize ...
//                factoryBean.setTransactionFactory(new MultiDataSourceTransactionFactory());
//                factoryBean.setTransactionFactory(new AtomikosTransactionFactory());
//                factoryBean.setTransactionFactory(new DynamicDataSourceTransactionFactory());
                factoryBean.setTransactionFactory(new DynamicTransactionFactory());
                factoryBean.setDataSource(dataSource);
            }
        };
    }

//    @Bean(name = "userTransaction")
//    @SneakyThrows(Exception.class)
//    public UserTransaction userTransaction() {
//        final UserTransactionImp userTransactionImp = new UserTransactionImp();
//        userTransactionImp.setTransactionTimeout(20000);
//        return userTransactionImp;
//    }
//
//    @Bean(name = "atomikosTransactionManager")
//    @SneakyThrows(Exception.class)
//    public TransactionManager atomikosTransactionManager() {
//        final UserTransactionManager userTransactionManager = new UserTransactionManager();
//        userTransactionManager.setForceShutdown(true);
//        return userTransactionManager;
//    }
//
//    @Bean(name = "transactionManager")
//    @SneakyThrows(Throwable.class)
//    @DependsOn({"userTransaction", "atomikosTransactionManager"})
////    @Qualifier("atomikosTransactionManager") TransactionManager atomikosTransactionManager, @Qualifier("userTransaction") UserTransaction userTransaction/
//    public JtaTransactionManager transactionManager() {
//        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(userTransaction(), atomikosTransactionManager());
//        jtaTransactionManager.setAllowCustomIsolationLevels(true);
//        return jtaTransactionManager;
//    }

    /**
     * 事务管理器
     *
     * @param dynamicDataSource 动态数据源
     * @return
     */
    @ConditionalOnMissingBean
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    /**
     * 遍历数据源配置并加载
     *
     * @return
     */
    private Map<Object, Object> getDynamicDataSource() {
        DruidProperties druid = dynamicDataSourceProperties.getDruid();
        Map<String, DataSourceProperties> dataSourcePropertiesMap = dynamicDataSourceProperties.getDatasource();
        Map<Object, Object> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size());
        dataSourcePropertiesMap.forEach((k, v) -> {
            DataSourceProperties mergeProperties = DynamicDataSourceUtils.merge(v, druid);
            DruidDataSource dataSource = DataSourceFactory.createDataSource(mergeProperties);
//            final AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
//            xaDataSource.setXaDataSource((DruidXADataSource) dataSource);
//            xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
//            xaDataSource.setUniqueResourceName(k);
//            xaDataSource.setPoolSize(dataSource.getMaxPoolPreparedStatementPerConnectionSize());
//            xaDataSource.setMinPoolSize(dataSource.getMinIdle());
//            xaDataSource.setMaxPoolSize(dataSource.getMaxActive());
//            xaDataSource.setMaxIdleTime(dataSource.getMinIdle());
//            xaDataSource.setMaxLifetime((int) dataSource.getMinEvictableIdleTimeMillis());
//            xaDataSource.setConcurrentConnectionValidation(true);
//            xaDataSource.setTestQuery(dataSource.getValidationQuery());
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
