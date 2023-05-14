package top.zsmile.system.modules.generator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import top.zsmile.system.modules.generator.dao.GeneratorDao;
import top.zsmile.system.modules.generator.dao.MysqlGeneratorDao;

@Configuration
public class DatabaseConfig {

    @Autowired
    private MysqlGeneratorDao mysqlGeneratorDao;

    @Bean
    @Primary
    public GeneratorDao generatorDao() {
        return mysqlGeneratorDao;
    }
}
