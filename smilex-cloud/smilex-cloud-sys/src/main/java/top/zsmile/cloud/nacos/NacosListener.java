package top.zsmile.cloud.nacos;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;
import top.zsmile.common.datasource.DataSourceFactory;
import top.zsmile.common.datasource.DynamicDataSource;
import top.zsmile.common.datasource.DynamicDataSourceConfig;
import top.zsmile.common.datasource.properties.DataSourceProperties;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.concurrent.Executor;

/**
 * nacos监听
 *
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/05/17/14:42
 * @ClassName: NacosListener
 * @Description: NacosListener
 */
@Slf4j
@RefreshScope
@Configuration
public class NacosListener implements InitializingBean {

    @Value("${smilex.nacos.listener.dataId}")
    private String dataId;

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Resource
    private DynamicDataSource dynamicDataSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        NacosConfigProperties nacosConfigProperties = nacosConfigManager.getNacosConfigProperties();
        log.info(nacosConfigProperties.toString());

        ConfigService configService = nacosConfigManager.getConfigService();
        NacosConfigProperties.Config config = nacosConfigProperties.getSharedConfigs().get(0);
        configService.addListener(dataId, config.getGroup(), new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info(configInfo);
                Yaml yaml = new Yaml();
                JSONObject infoJson = new JSONObject(yaml.load(configInfo));
                refreshDataSource(infoJson);
            }
        });
    }

    /**
     * 刷新数据库
     */
    private void refreshDataSource(JSONObject infoJson) {
        JSONObject druid = getProperties(infoJson, "spring.datasource.druid");
        log.info(druid.toJSONString());
        if (druid != null) {
            DataSourceProperties dataSourceProperties = druid.toJavaObject(DataSourceProperties.class);
//            DataSource dataSource = (DataSource) dynamicDataSource.getDataSource(DynamicDataSourceConfig.MASTER);
//            DataSourceFactory.createDataSource(dataSource, dataSourceProperties);
            dynamicDataSource.replaceDataSource(DynamicDataSourceConfig.MASTER, dataSourceProperties);

        }
    }

    private JSONObject getProperties(JSONObject infoJson, String properties) {
        if (StringUtils.isNotBlank(properties)) {
            return loopProperties(infoJson, properties.split("\\."), 0);
        }
        return null;
    }

    private JSONObject loopProperties(JSONObject infoJson, String[] properties, int index) {
        if (infoJson.containsKey(properties[index])) {
            if (index < properties.length - 1) {
                return loopProperties(infoJson.getJSONObject(properties[index]), properties, index + 1);
            } else {
                return infoJson.getJSONObject(properties[index]);
            }
        }
        return null;
    }
}
