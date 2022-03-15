[toc]

---

# druid 
## Maven引入

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.8</version>
</dependency>
```



## 多数据源

### 配置

1. 设置Datasource properties

```java
@Data
public class DataSourceProperties {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    /**
     * Druid默认参数
     */
    private int initialSize = 5;
    private int maxActive = 20;
    private int minIdle = 5;
    private long maxWait = 60 * 1000L;
    private long timeBetweenEvictionRunsMillis = 60 * 1000L;
    private long minEvictableIdleTimeMillis = 1000L * 60L * 30L;
    private long maxEvictableIdleTimeMillis = 1000L * 60L * 60L * 7;
    private String validationQuery = "select 1 from DUAL";
    private int validationQueryTimeout = -1;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;
    private boolean testWhileIdle = true;
    private boolean poolPreparedStatements = true;
    private int maxOpenPreparedStatements = -1;
    private boolean sharePreparedStatements = false;
    private String filters = "stat,wall";
}
```

2. yml配置

```yaml
spring:
  datasource:
    druid:
      # mysql 连接信息
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://127.0.0.1:3306/smilex-boot?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
      username: root
      password: root
      # 其它配置
      time-between-eviction-runs-millis: 60000
      keep-alive: true
      initial-size: 5
      min-idle: 5
      max-active: 20
      max-wait: 60000
      min-evictable-idle-time-millis: 300000
      validation-query: SELECT 1 FROM DUAL
      test-while-idle: true
      test-on-borrow: false
      test-on-return: false
      pool-prepared-statements: true
      # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
      filters: stat,wall
      stat-view-servlet:
        enabled: true
        allow: 127.0.0.1
        url-pattern: /druid/*
```

3. 实现AbstractRoutingDataSource

```java

/**
 * 实现多数数据源控制
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static DynamicDataSource INSTANCE;

    private static Map<Object, Object> dataSourceMap = new HashMap<>();

    private static final ReentrantLock lock = new ReentrantLock();

    public static DynamicDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (DynamicDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DynamicDataSource();
                }
            }
        }
        return INSTANCE;
    }

    public void addDataSource(Object key, DataSourceProperties dataSourceProperties) {
        DataSource dataSource = DataSourceFactory.createDataSource(dataSourceProperties);
        addDataSource(key, dataSource);
    }

    public void addDataSource(Object key, DataSource dataSource) {
        Object o = dataSourceMap.get(key);
        if (o != null) {
//            if (o instanceof DataSource) {
//                DruidDataSource druidDataSource = (DruidDataSource) o;
//                druidDataSource.close();
//            }
            throw new SXException("数据库连接池 KEY 重复");
        }
        dataSourceMap.put(key, dataSource);
        setDataSourceMap(dataSourceMap);
    }

    public void setDataSourceMap(Map<Object, Object> objectObjectMap) {
        lock.lock();
        this.dataSourceMap = objectObjectMap;
        super.setTargetDataSources(dataSourceMap);
        super.afterPropertiesSet();
        lock.unlock();
    }

    public void delDataSource(Object key) {
        Object o = dataSourceMap.get(key);
        if (o != null) {
            if (o instanceof DataSource) {
                DruidDataSource dataSource = (DruidDataSource) o;
                if (dataSource != null) {
                    dataSource.close();
                    dataSource = null;
                    dataSourceMap.remove(key);
                    setDataSourceMap(dataSourceMap);
                }
            } else {
                throw new SXException("数据池类型无法识别");
            }
        }
    }

    /**
     * Recording to the datasource of the map with the key
     * @param key
     * @param dataSource
     */
    public void replaceDataSource(Object key, DataSource dataSource) {
        dataSourceMap.put(key, dataSource);
        setDataSourceMap(dataSourceMap);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContentHolder.getDataSource();
    }
}
```

4. 配置Spring DynamicDatasouce Bean

```java

@Configuration
@Order(-1)
public class DynamicDataSourceConfig {


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
        dynamicDataSource.addDataSource("master", dataSource);
        dynamicDataSource.setDefaultTargetDataSource(dataSource);
        return dynamicDataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}
```

5. 设置DataSourceFactory 工厂，用于根据配置生成DataSource

```java
public class DataSourceFactory {
    public static DataSource createDataSource(DataSourceProperties properties) {
        DruidDataSource dataSource = new DruidDataSource();
        BeanUtils.copyProperties(properties, dataSource);
        return dataSource;
    }

    public static DataSource createDataSource(DataSource dataSource, DataSourceProperties properties) {
        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        BeanUtils.copyProperties(properties, druidDataSource);
        return druidDataSource;
    }
}
```

### 使用方式

#### 注解

1. 添加注解 DataSource

```java
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String value() default DynamicDataSourceConfig.MASTER;
}
```

2. 添加拦截器 DataSourceAspect

```java
@Aspect
@Component
@Slf4j
public class DataSourceAspect {
    @Pointcut("@within(top.zsmile.core.datasource.annotation.DataSource) || @annotation(top.zsmile.core.datasource.annotation.DataSource)")
    public void dataSourceAspect() {

    }

    @Before("dataSourceAspect()")
    public void beforeSwitch(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DataSource methodDataSource = method.getAnnotation(DataSource.class);
        String value = null;
        if (methodDataSource != null) {
            value = methodDataSource.value();
        } else {
            Class<?> aClass = joinPoint.getTarget().getClass();
            DataSource annotation = aClass.getAnnotation(DataSource.class);
            if (annotation != null) {
                value = annotation.value();

            }
        }
        if (checkValue(value)) {
            DataSourceContentHolder.setDataSource(value);
        }
    }

    private boolean checkValue(final String value) {
        if (!StringUtils.isEmpty(value)) {
            boolean containKey = DynamicDataSource.getInstance().containKey(value);
            if (!containKey) {
                throw new SXException("数据源" + value + "未准备");
            }
        }
        return true;
    }

    @After("dataSourceAspect()")
    public void afterSwitchDS() {
        DataSourceContentHolder.clear();
    }
}
```

3. 在需要切换数据源的地方添加注解



## 源码解析

### AbstractRoutingDataSource 
我们先看一下这个类里面包含了几个变量。
```java
	@Nullable
	private Map<Object, Object> targetDataSources;

	@Nullable
	private Object defaultTargetDataSource;

	private boolean lenientFallback = true;

	private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

	@Nullable
	private Map<Object, DataSource> resolvedDataSources;

	@Nullable
	private DataSource resolvedDefaultDataSource;
```
这里我们先简单的说一下各个变量的作用：
- targetDataSources：存储未解析的目标的数据源Map
- defaultTargetDataSource: 未解析的默认的目标数据源
- lenientFallback：回退。稍后再说
- dataSourceLookup：dataSource解析器，默认是JNDI
- resolvedDataSources：将目标的数据源解析并保存成实际的数据链接后的map
- resolvedDefaultDataSource: 默认的数据源

#### Method afterPropertiesSet
> afterPropertiesSet 是用来解析targetDataSources 来获取真正的dataSource
```java
@Override
public void afterPropertiesSet() {
    if (this.targetDataSources == null) {
        throw new IllegalArgumentException("Property 'targetDataSources' is required");
    }
    this.resolvedDataSources = new HashMap<>(this.targetDataSources.size());
    this.targetDataSources.forEach((key, value) -> {
        Object lookupKey = resolveSpecifiedLookupKey(key);
        DataSource dataSource = resolveSpecifiedDataSource(value);
        this.resolvedDataSources.put(lookupKey, dataSource);
    });
    if (this.defaultTargetDataSource != null) {
        this.resolvedDefaultDataSource = resolveSpecifiedDataSource(this.defaultTargetDataSource);
    }
}
```

1. 该方法会先判断targetDataSources是否为空，如果为空抛出异常。
2. 新建一下HashMap，大小为targetDataSources的大小，然后赋值给resolvedDataSources
3. 遍历targetDataSources，解析Key和DataSource，并存储到resolvedDataSources
4. 如果设置了 defaultTargetDataSource【默认数据源】，则会将默认数据源解析后，存储到resolveDefaultDataSource 

以上的流程可以看出，该方法必须在设置了targetDataSources后调用，才能生效。

所以我们在做多数据源时，有时会看到这样的代码：这是为了解析我们配置的数据源Bean。
```java
AbstractRoutingDataSource.setTargetDataSources(Map(...))
AbstractRoutingDataSource.afterPropertiesSet();
```


#### Method resolveSpecifiedDataSource

1. 当参数DataSource的类型为DataSource时候，则返回
2. 如果是String时，因为默认时使用的JNDILookUp，所以这里会默认去根据JNDI解析。如果需要替换不同的datasourceLookUp，Spring内置了4种：
    - BeanFactoryDataSourceLookup：在SpringBean容器里面查找dataSource。
    - JndiDataSourceLookup：从JNDI数据源解析
    - MapDataSourceLookup：从Map容器解析
    - SingleDataSourceLookup：从单数据源直接返回
3. 如果不是以上两种类型，则抛出异常信息。

```java
/**
 * Resolve the specified data source object into a DataSource instance.
 * <p>The default implementation handles DataSource instances and data source
 * names (to be resolved via a {@link #setDataSourceLookup DataSourceLookup}).
 * @param dataSource the data source value object as specified in the
 * {@link #setTargetDataSources targetDataSources} map
 * @return the resolved DataSource (never {@code null})
 * @throws IllegalArgumentException in case of an unsupported value type
 */
protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
    if (dataSource instanceof DataSource) {
        return (DataSource) dataSource;
    }
    else if (dataSource instanceof String) {
        return this.dataSourceLookup.getDataSource((String) dataSource);
    }
    else {
        throw new IllegalArgumentException(
                "Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
    }
}

```

```java
 protected <T> T lookup(String jndiName, @Nullable Class<T> requiredType) throws NamingException {
    Assert.notNull(jndiName, "'jndiName' must not be null");
    String convertedName = this.convertJndiName(jndiName);

    Object jndiObject;
    try {
        jndiObject = this.getJndiTemplate().lookup(convertedName, requiredType);
    } catch (NamingException var6) {
        if (convertedName.equals(jndiName)) {
            throw var6;
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Converted JNDI name [" + convertedName + "] not found - trying original name [" + jndiName + "]. " + var6);
        }

        jndiObject = this.getJndiTemplate().lookup(jndiName, requiredType);
    }

    if (this.logger.isDebugEnabled()) {
        this.logger.debug("Located object with JNDI name [" + convertedName + "]");
    }

    return jndiObject;
}
```

