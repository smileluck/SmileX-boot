package top.zsmile.system.modules.generator.config;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import top.zsmile.system.modules.generator.constant.DefaultConstants;
import top.zsmile.system.modules.generator.properties.GeneratorProperties;

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
