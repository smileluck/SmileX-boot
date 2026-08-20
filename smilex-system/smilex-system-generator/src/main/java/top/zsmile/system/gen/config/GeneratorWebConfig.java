package top.zsmile.system.gen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 生成器 Web 配置：注册 Token 门禁过滤器
 */
@Configuration
public class GeneratorWebConfig {

    @Bean
    public FilterRegistrationBean generatorSecurityFilter(
            @Value("${smilex.generator.security.token:}") String token) {
        FilterRegistrationBean<GeneratorSecurityFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new GeneratorSecurityFilter(token));
        registration.addUrlPatterns("/generator/*", "/druid/*");
        registration.setOrder(1);
        return registration;
    }
}
