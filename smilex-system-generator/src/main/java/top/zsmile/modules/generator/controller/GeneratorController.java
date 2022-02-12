package top.zsmile.modules.generator.controller;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zsmile.core.api.R;
import top.zsmile.core.datasource.DataSourceFactory;
import top.zsmile.core.datasource.DynamicDataSource;
import top.zsmile.core.datasource.properties.DataSourceProperties;
import top.zsmile.core.exception.SXException;
import top.zsmile.modules.generator.constant.DefaultConstants;
import top.zsmile.modules.generator.entity.DatabaseConnEntity;
import top.zsmile.modules.generator.service.GeneratorSerivce;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private GeneratorSerivce generatorService;

    @GetMapping("/list")
    public R list(String tableName) {
        List<Map<String, Object>> maps = generatorService.queryTableList(tableName);
        return R.success(maps);
    }

    @GetMapping("/info")
    public R info(String tableName) {
        Map<String, String> maps = generatorService.queryTable(tableName);
        return R.success(maps);
    }

    @GetMapping("/columns")
    public R columns(String tableName) {
        List<Map<String, Object>> maps = generatorService.queryTableColumns(tableName);
        return R.success(maps);
    }

    @PostMapping("/connect")
    public R connect(DatabaseConnEntity databaseConnEntity) {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUsername(databaseConnEntity.getUsername());
        dataSourceProperties.setPassword(databaseConnEntity.getPassword());
        dataSourceProperties.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceProperties.setUrl(databaseConnEntity.getUrl());
        DruidDataSource dataSource = (DruidDataSource) DataSourceFactory.createDataSource(dataSourceProperties);
        try {
            dataSource.getConnection();
        } catch (SQLException throwables) {
            throw new SXException("数据库连接异常");
        }
        DynamicDataSource.getInstance().delDataSource(DefaultConstants.GENERATOR_DATASOURCE_KEY);
        DynamicDataSource.getInstance().addDataSource(DefaultConstants.GENERATOR_DATASOURCE_KEY, dataSource);
        return R.success("连接成功");
    }


}
