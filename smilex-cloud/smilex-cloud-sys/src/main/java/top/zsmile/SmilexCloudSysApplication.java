package top.zsmile;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/05/15/15:41
 * @ClassName: SmilexCloudSysApplication
 * @Description: SmilexCloudSysApplication
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class SmilexCloudSysApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SmilexCloudSysApplication.class, args);
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        String applicationName = env.getProperty("spring.application.name");
        path = StringUtils.isNotBlank(path) ? path : "";
        log.info("\n----------------------------------------------------------\n\t" +
                "Application " + applicationName + " is running! Access URLs:\n\t" +
                "Local: \t\t    \thttp://localhost:" + port + path + "/\n\t" +
                "External: \t\thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
