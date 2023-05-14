package top.zsmile.system.modules.generator.properties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: B.Smile
 * @Date: 2022/3/15 16:36
 * @Description:
 */
@Data
@Configuration
@PropertySource("classpath:/config/generator.properties")
public class GeneratorProperties {
    /**
     * 包路径
     */
    private String packages;
    /**
     * 作者
     */
    private String author;
    /**
     * 邮箱
     */
    private String email;
}
