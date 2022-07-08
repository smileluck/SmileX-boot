package top.zsmile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
import top.zsmile.common.constant.CommonConstant;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).enable(true)
                .select()
                /***
                 重要的两个方法:
                 apis():指定要生成文档的接口包基本路径
                 paths():指定针对哪些请求生成接口文档
                 参考官方资料：http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
                 ****/
                // 1. 将 top.zsmile 设置为基础包扫描。
                // 2. 只包含 RestController 注解，也可以使用 Api 注解，但是对应的类需要添加。
                // 3. 针对所有路径扫描
                .apis(RequestHandlerSelectors.basePackage("top.zsmile"))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securityScheme())
                .securityContexts(securityContexts());

    }

    /**
     * 安全计划
     * 使用 X-ACCESS-TOKEN 作为请求头，添加在 HTTP Header 里面
     *
     * @return
     */
    @Bean
    public List<ApiKey> securityScheme() {
        return Collections.singletonList(new ApiKey(CommonConstant.X_ACCESS_TOKEN, CommonConstant.X_ACCESS_TOKEN, "header"));
    }

    /**
     * 新增 securityContexts 保持登录状态
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(defaultAuth())
                //设置需要登录认证的路径，非noauth开头的都需要Token认证
                .forPaths(PathSelectors.regex("^(?!noauth).*$"))
                .build());

    }

    /**
     * 默认授权
     *
     * @return
     */
    private List<SecurityReference> defaultAuth() {
        // 权限范围：Scope=>全局，设置在keyName=>X-ACCESS-TOKEN
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(CommonConstant.X_ACCESS_TOKEN, authorizationScopes));
    }

    /**
     * 文档信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        Contact smileX = new Contact("SmileX", "https://github.com/smileluck", "");
        return new ApiInfoBuilder()
                .title("SmileX项目接口文档")
                .description("API接口文档")
                .version("1.0.0")
                .termsOfServiceUrl("https://github.com/smileluck")
                .contact(smileX)
                .build();
    }
}
