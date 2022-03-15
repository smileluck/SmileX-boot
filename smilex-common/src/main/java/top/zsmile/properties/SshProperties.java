package top.zsmile.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Configuration
@Data
@PropertySource(value = "classpath:/config/remote-ssh.properties", encoding = "utf-8", ignoreResourceNotFound = true)
@ConditionalOnResource(resources = {"classpath:/config/remote-ssh.properties"})
@ConfigurationProperties(prefix = "ssh")
public class SshProperties {
    //    @Value("${ssh.enabled:false}")
    private boolean enabled;

    @Bean
    public Remote getRemote() {
        return new Remote();
    }

    @Bean
    public Local getLocal() {
        return new Local();
    }

    @Data
    @Configuration
    @ConfigurationProperties("ssh.remote")
    public class Remote {
        //    @Value("${ssh.remote.ip}")
        private String ip;
        //    @Value("${ssh.remote.port}")
        private int port;
        //    @Value("${ssh.remote.username}")
        private String username;
        //    @Value("${ssh.remote.password}")
        private String passowrd;
        //    @Value("${ssh.remote.target_host}")
        private String targetHost;
        //    @Value("${ssh.remote.target_port}")
        private int targetPort;
    }

    @Data
    @Configuration
    @ConfigurationProperties("ssh.local")
    public class Local {
        //    @Value("${ssh.local.resource_host}")
        private String resourceHost;
        //    @Value("${ssh.local.resource_port}")
        private int resourcePort;
    }

}
