package top.zsmile.system.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/17/16:49
 * @ClassName: ShiroSysConfig
 * @Description: ShiroSysConfig
 */
@Slf4j
@Configuration
public class ShiroSysConfig {

    /**
     * 配置拦截
     *
     * @return
     */
    @Bean("ShiroFilterChainDefinition")
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        /*swagger*/
        chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
        chainDefinition.addPathDefinition("/doc.html", "anon");
        chainDefinition.addPathDefinition("/webjars/**", "anon");
        chainDefinition.addPathDefinition("/v2/api-docs", "anon");
        chainDefinition.addPathDefinition("/v3/api-docs", "anon");
        chainDefinition.addPathDefinition("/v3/api-docs/**", "anon");
        chainDefinition.addPathDefinition("/swagger-resources", "anon");

        /*SysLogin*/
        chainDefinition.addPathDefinition("/sys/login/submit", "anon");
        chainDefinition.addPathDefinition("/sys/login/captcha/*", "anon");

        /*开放博客接口*/
        chainDefinition.addPathDefinition("/open/blog/**", "anon");
        chainDefinition.addPathDefinition("/open/git/**", "anon");

        /*开放demo接口*/
        chainDefinition.addPathDefinition("/demo/**", "anon");

        /*开放APP接口*/
        chainDefinition.addPathDefinition("/app/demo/**", "anon");

        /*开放APP接口*/
        chainDefinition.addPathDefinition("/druid/**", "anon");

        chainDefinition.addPathDefinition("/**", "oauth2");
        return chainDefinition;
    }
}
