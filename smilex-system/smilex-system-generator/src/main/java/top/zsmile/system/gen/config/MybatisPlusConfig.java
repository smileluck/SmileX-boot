package top.zsmile.system.gen.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = {"top.zsmile.tool.**.dao"})
public class MybatisPlusConfig {

}
