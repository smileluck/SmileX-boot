[toc]

---

# springboot 下mybatis-plus配置方式

1. 基于yml配置

```yaml
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  global-config:
    banner: false
    db-config:
      #主键类型  0:"数据库ID自增",1:"该类型为未设置主键类型", 2:"用户输入ID",3:"全局唯一ID (数字类型唯一ID)", 4:"全局唯一ID UUID",5:"字符串全局唯一ID (idWorker 的字符串表示)";
      id-type: ASSIGN_ID
      logic-delete-field: del_flag
      logic-delete-value: 1
      logic-not-delete-value: 0
      table-underline: true
  configuration:
    # 这个配置会将执行的sql打印出来，在开发或测试的时候可以用
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    # 返回类型为Map,显示null对应的字段
    call-setters-on-nulls: true
```

2. 基于Springboot 的SqlSessionFactory Bean 注入

```java

@Bean(name = "sqlSessionFactory")
public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
    throws Exception {
    final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();

    GlobalConfig globalConfig = new GlobalConfig();
    globalConfig.setBanner(false);

    GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
    dbConfig.setIdType(IdType.ASSIGN_ID);
    dbConfig.setLogicDeleteField("del_flag");
    dbConfig.setLogicDeleteValue("1");
    dbConfig.setLogicNotDeleteValue("0");
    dbConfig.setTableUnderline(true);
    globalConfig.setDbConfig(dbConfig);


    MybatisConfiguration configuration = new MybatisConfiguration();
    configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
    configuration.setCallSettersOnNulls(true);

    sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/**/*.xml"));
    sessionFactory.setGlobalConfig(globalConfig);
    sessionFactory.setDataSource(dynamicDataSource);
    sessionFactory.setConfiguration(configuration);
    return sessionFactory.getObject();
}
```

