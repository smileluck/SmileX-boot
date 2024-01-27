package top.zsmile.common.core.config;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Value;
import top.zsmile.common.core.utils.IPUtils;

public class SystemInfo implements SmartInitializingSingleton {
    private static SystemInfo systemInfo;
    private String hostName;
    private String ip;

    @Value("${server.port:80}")
    private Integer port;

    private String ipWithPort;

    public String getHostName() {
        return hostName;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getIpWithPort() {
        return ipWithPort;
    }


    @Override
    public void afterSingletonsInstantiated() {
        if (systemInfo == null) {
            synchronized (this.getClass()) {
                systemInfo = new SystemInfo();
                systemInfo.ip = IPUtils.getServerIp();
                systemInfo.ipWithPort = String.format("%s:%d", systemInfo.ip, systemInfo.port);
            }
        }
    }
}
