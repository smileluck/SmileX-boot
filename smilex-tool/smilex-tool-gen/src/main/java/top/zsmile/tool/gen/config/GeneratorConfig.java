package top.zsmile.tool.gen.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import top.zsmile.tool.gen.properties.GeneratorProperties;

/**
 * @author: B.Smile
 * @Date: 2022/3/15 16:36
 * @Description:
 */
@Configuration
@Data
public class GeneratorConfig {

    @Autowired
    private GeneratorProperties generatorProperties;


}
