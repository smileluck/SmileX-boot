package top.zsmile.modules.generator.controller;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.zsmile.core.api.R;
import top.zsmile.core.datasource.DataSourceFactory;
import top.zsmile.core.datasource.DynamicDataSource;
import top.zsmile.core.datasource.properties.DataSourceProperties;
import top.zsmile.core.exception.SXException;
import top.zsmile.modules.generator.constant.DefaultConstants;
import top.zsmile.modules.generator.entity.DatabaseConnEntity;
import top.zsmile.modules.generator.entity.GeneratorEntity;
import top.zsmile.modules.generator.service.GeneratorSerivce;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Validated
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
    public R info(@NotBlank String tableName) {
        Map<String, String> maps = generatorService.queryTable(tableName);
        if (maps == null) {
            return R.fail("查询不到该表结构");
        } else {
            return R.success(maps);
        }
    }

    @GetMapping("/columns")
    public R columns(@NotBlank String tableName) {
        List<Map<String, Object>> maps = generatorService.queryTableColumns(tableName);
        return R.success(maps);
    }

    @PostMapping("/connect")
    public R connect(@Validated @RequestBody DatabaseConnEntity databaseConnEntity) {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUsername(databaseConnEntity.getUsername());
        dataSourceProperties.setPassword(databaseConnEntity.getPassword());
        dataSourceProperties.setDriverClassName(DefaultConstants.MYSQL_DRIVER_CLASS);
        dataSourceProperties.setUrl(databaseConnEntity.getUrl());
        DruidDataSource dataSource = (DruidDataSource) DataSourceFactory.createDataSource(dataSourceProperties);
        try {
            dataSource.getConnection();
        } catch (SQLException throwables) {
            dataSource.close();
            throw new SXException("数据库连接异常");
        }
//        DynamicDataSource.getInstance().delDataSource(DefaultConstants.GENERATOR_DATASOURCE_KEY);
        DynamicDataSource.getInstance().replaceDataSource(DefaultConstants.GENERATOR_DATASOURCE_KEY, dataSource);
        return R.success("连接成功");
    }

    @PostMapping("/genSingleFile")
    public R genSingleFile(@Validated @RequestBody GeneratorEntity generatorEntity, HttpServletResponse response) {

//        response.setContentType("application/octet-stream");
//        response.setCharacterEncoding("utf-8");
//        response.setContentLength((int) file.length());
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        return R.success();
    }
}
