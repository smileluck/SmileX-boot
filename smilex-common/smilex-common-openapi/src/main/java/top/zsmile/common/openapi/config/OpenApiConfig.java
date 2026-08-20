package top.zsmile.common.openapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * springdoc OpenAPI 基础信息配置
 * <p>
 * 标题默认取 spring.application.name，可通过 smilex.openapi.title / version 覆盖。
 * 文档契约端点 /v3/api-docs，UI 层可自行替换。
 */
@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name:SmileX}")
    private String applicationName;

    @Value("${smilex.openapi.title:}")
    private String title;

    @Value("${smilex.openapi.version:v1.0}")
    private String version;

    @Bean
    public OpenAPI smilexOpenApi() {
        String apiTitle = (title == null || title.isEmpty()) ? applicationName + " Docs" : title;
        return new OpenAPI().info(new Info()
                .title(apiTitle)
                .description("Api Doc")
                .version(version));
    }
}
