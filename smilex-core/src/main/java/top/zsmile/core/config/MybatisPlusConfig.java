package top.zsmile.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value={"top.zsmile.modules.**.dao"})
public class MybatisPlusConfig {
}
