package top.zsmile.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import top.zsmile.core.datasource.DataSourceFactory;
import top.zsmile.core.datasource.DynamicDataSource;
import top.zsmile.core.datasource.properties.DataSourceProperties;
import top.zsmile.modules.generator.constant.DefaultConstants;

import javax.sql.DataSource;

@Component
@Slf4j
public class GeneratorRunner implements CommandLineRunner {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Override
    public void run(String... args) throws Exception {
        log.info("======初始化数据源 " + DefaultConstants.GENERATOR_DATASOURCE_KEY + " ======");
        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();
        DataSource dataSource = DataSourceFactory.createDataSource(dataSourceProperties);
        dynamicDataSource.addDataSource(DefaultConstants.GENERATOR_DATASOURCE_KEY, dataSource);
    }
}
