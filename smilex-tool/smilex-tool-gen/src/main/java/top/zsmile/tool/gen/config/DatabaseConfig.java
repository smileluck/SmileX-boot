package top.zsmile.tool.gen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import top.zsmile.tool.gen.dao.GeneratorDao;
import top.zsmile.tool.gen.dao.MysqlGeneratorDao;

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
