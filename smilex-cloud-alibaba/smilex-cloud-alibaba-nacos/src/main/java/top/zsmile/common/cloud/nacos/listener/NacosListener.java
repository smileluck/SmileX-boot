package top.zsmile.common.cloud.nacos.listener;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;
import top.zsmile.common.cloud.nacos.constants.NacosConstant;
import top.zsmile.common.datasource.ds.DynamicDataSource;
import top.zsmile.common.datasource.properties.DataSourceProperties;
import top.zsmile.common.datasource.properties.DynamicDataSourceProperties;

import javax.annotation.Resource;
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
@ConditionalOnProperty(prefix = NacosConstant.REFRESH_DB_PREFIX, name = NacosConstant.REFRESH_DB_ENABLED, havingValue = "true")
public class NacosListener implements InitializingBean {

    @Resource
    private NacosRefreshDb nacosRefreshDb;

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Resource
    private NacosConfigManager nacosConfigManager;

    @Resource
    private DynamicDataSource dynamicDataSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        NacosConfigProperties nacosConfigProperties = nacosConfigManager.getNacosConfigProperties();
        log.info(nacosConfigProperties.toString());

        ConfigService configService = nacosConfigManager.getConfigService();
        NacosConfigProperties.Config config = nacosConfigProperties.getSharedConfigs().get(0);

        // 刷新数据库
        configService.addListener(nacosRefreshDb.getDataId(), nacosRefreshDb.compareGroup(config.getGroup()), new Listener() {
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
     * 刷新连接池
     *
     * @param infoJson
     */
    private void refreshDataSource(JSONObject infoJson) {
        JSONObject druid = getProperties(infoJson, "spring.datasource.dynamic.datasource.master");
        log.info(druid.toJSONString());
        if (druid != null) {
            DataSourceProperties dataSourceProperties = druid.toJavaObject(DataSourceProperties.class);
            dynamicDataSource.replace(DynamicDataSourceProperties.PRIMARY, dataSourceProperties);
        }
    }

    /**
     * 获取配置
     *
     * @param infoJson
     * @param properties
     * @return
     */
    private JSONObject getProperties(JSONObject infoJson, String properties) {
        if (StringUtils.isNotBlank(properties)) {
            return loopProperties(infoJson, properties.split("\\."), 0);
        }
        return null;
    }

    /**
     * 遍历配置
     *
     * @param infoJson
     * @param properties
     * @param index
     * @return
     */
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
