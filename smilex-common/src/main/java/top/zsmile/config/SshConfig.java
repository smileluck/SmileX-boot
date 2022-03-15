package top.zsmile.config;


import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import top.zsmile.properties.SshProperties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@Component
@Slf4j
@ConditionalOnBean(SshProperties.class)
public class SshConfig implements ServletContextInitializer {
    private static Session session;

    @Autowired(required = false)
    private SshProperties sshProperties;


    public Session initSession() {
        try {
            //如果配置文件包含ssh.enabled属性，则使用ssh转发
            if (sshProperties.isEnabled()) {
                Session session = new JSch().getSession(sshProperties.getRemote().getUsername(), sshProperties.getRemote().getIp(), sshProperties.getRemote().getPort());
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPassword(sshProperties.getRemote().getPassowrd());
                session.connect();
                //将本地端口的请求转发到目标地址的端口
                session.setPortForwardingL(sshProperties.getLocal().getResourceHost(), sshProperties.getLocal().getResourcePort(), sshProperties.getRemote().getTargetHost(), sshProperties.getRemote().getTargetPort());
                log.info("ssh forward is open.");
                return session;
            } else {
                log.info("ssh forward is closed.");
            }
        } catch (JSchException e) {
            log.error("ssh JSchException failed.", e);
        } catch (Exception e) {
            log.error("ssh settings is failed. skip!", e);
        }
        return null;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        session = initSession();
    }

    /**
     * 断开SSH连接
     */
    public void destroy() {
        this.session.disconnect();
    }
}
