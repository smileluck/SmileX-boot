package top.zsmile.common.datasource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
//@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DataSourceProperties {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    /**
     * Druid默认参数
     */
    private int initialSize;
    private int maxActive;
    private int minIdle;
    private long maxWait;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private long maxEvictableIdleTimeMillis;
    private String validationQuery;
    private int validationQueryTimeout;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private Boolean testWhileIdle;
    private Boolean poolPreparedStatements;
    private int maxOpenPreparedStatements;
    private Boolean sharePreparedStatements;
    private String filters;
    private String connectionProperties;
}
