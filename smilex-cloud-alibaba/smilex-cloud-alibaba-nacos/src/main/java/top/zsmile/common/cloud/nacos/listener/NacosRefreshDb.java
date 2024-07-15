package top.zsmile.common.cloud.nacos.listener;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * nacos 自定义刷新配置
 */
@Configuration
public class NacosRefreshDb {

    @Value("${smilex.nacos.listener.refresh-db.enabled}")
    private Boolean enabled;

    @Value("${smilex.nacos.listener.refresh-db.dataId}")
    private String dataId;

    @Value("${smilex.nacos.listener.refresh-db.group}")
    private String group;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String compareGroup(String group) {
        if (StringUtils.isNotBlank(this.group)) {
            return this.group;
        } else {
            return group;
        }
    }
}
