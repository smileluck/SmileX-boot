package top.zsmile.common.datasource.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = DynamicDataSourceProperties.PREFIX)
public class DynamicDataSourceProperties {
    public static final String PREFIX = "spring.datasource.dynamic";

    /**
     * 默认数据源,master
     */
    public static final String PRIMARY = "master";

    /**
     * 所有数据源配置
     */
    private Map<String, DataSourceProperties> datasource = new LinkedHashMap<>();

    /**
     * Druid配置
     */
    private DruidProperties druid = new DruidProperties();
}
