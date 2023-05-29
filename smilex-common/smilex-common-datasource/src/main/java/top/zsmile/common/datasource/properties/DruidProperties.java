package top.zsmile.common.datasource.properties;

import lombok.Data;

@Data
public class DruidProperties {

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
    private Boolean testOnBorrow = false;
    private Boolean testOnReturn = false;
    private Boolean testWhileIdle = true;
    private Boolean poolPreparedStatements = true;
    private int maxOpenPreparedStatements = -1;
    private Boolean sharePreparedStatements = false;
    private String filters = "stat,wall";
    private String connectionProperties;
}
