[toc]

---

# 拦截器注入的方式

## Mybatis

1. 使用 @Component 注入 SpringBoot 容器。但无法控制执行顺序

```java
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
public class UpdateInterceptor implements Interceptor {
}
```

2. 使用 xml 配置。mybatis-config.xml

```xml
<plugins>
    <plugin interceptor="UpdateInterceptor">
        <property name="args1" value="参数示例"/>
    </plugin>
</plugins>	
```

3. 使用 `SqlSessionFactory`。需要注意的是，这是**倒序执行，后加入的先执行**

```java

@Resource
private List<SqlSessionFactory> sqlSessionFactories;

@PostConstruct
public void addInterceptor() {
    sqlSessionFactories.forEach(item -> {
        item.getConfiguration().addInterceptor(new DataScopeInterceptor());
        item.getConfiguration().addInterceptor(new DataScopeInterceptor2());
    });
}

```

## Mybatis-Plus

1. 使用 `ConfigurationCustomizer`  设置执行顺序，需要注意的是**倒序执行，后加入的先执行**

```java

@Configuration
public class MybatisConfig {
// 注册插件方式
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            //插件拦截链采用了责任链模式，执行顺序和加入连接链的顺序有关
            configuration.addInterceptor(new UpdateInterceptor());
        };
    }
}

```

2. 使用设置 `Mybatis-Plus` 下的 `Innerterceptor`

```java
@Bean
public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    //     数据权限过滤
    interceptor.addInnerInterceptor(new DataScopeInnerInterceptor());
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
    interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
    return interceptor;
}
```



# 拦截器使用案例

## 拦截填充创建时间和更新时间

```java

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
public class UpdateInterceptor implements Interceptor {
    @Autowired
    private CommonAuthApi commonAuthApi;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof Executor) {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object params = invocation.getArgs()[1];
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            operateTime(params, sqlCommandType);
        }
        return invocation.proceed();
    }

    private void operateTime(Object params, SqlCommandType sqlCommandType) {
        Map<String, Object> userInfo = commonAuthApi.queryUserInfo();
        if (sqlCommandType == SqlCommandType.UPDATE || sqlCommandType == SqlCommandType.DELETE) {
            if (params instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) params;
                LocalDateTime now = LocalDateTime.now();
                String username = userInfo.get("username").toString();
                baseEntity.setUpdateTime(now);
                baseEntity.setUpdateBy(username);
            } else if (params instanceof Map) {
                Map baseMap = (Map) params;
                LocalDateTime now = LocalDateTime.now();
                String username = userInfo.get("username").toString();
                baseMap.put("updateTime", now);
                baseMap.put("updateBy", username);
            }
        } else if (sqlCommandType == SqlCommandType.INSERT) {
            if (params instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) params;
                LocalDateTime now = LocalDateTime.now();
                String username = userInfo.get("username").toString();
                baseEntity.setCreateTime(now);
                baseEntity.setCreateBy(username);
                baseEntity.setUpdateTime(now);
                baseEntity.setUpdateBy(username);
            } else if (params instanceof Map) {
                Map baseMap = (Map) params;
                LocalDateTime now = LocalDateTime.now();
                String username = userInfo.get("username").toString();
                baseMap.put("createTime", now);
                baseMap.put("createBy", username);
                baseMap.put("updateTime", now);
                baseMap.put("updateBy", username);
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
```

