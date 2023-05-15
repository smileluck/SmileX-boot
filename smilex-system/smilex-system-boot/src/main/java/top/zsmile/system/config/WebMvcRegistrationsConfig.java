package top.zsmile.system.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import top.zsmile.system.modules.app.handler.ApiRequestMappingHandlerMapping;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/19/17:09
 * @ClassName: WebMvcRegistrationsConfig
 * @Description: WebMvcRegistrationsConfig
 */
@Configuration
public class WebMvcRegistrationsConfig implements WebMvcRegistrations {
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiRequestMappingHandlerMapping();
    }
}
