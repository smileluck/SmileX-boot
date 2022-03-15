package top.zsmile.modules.generator.service.impl;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zsmile.core.datasource.annotation.DataSource;
import top.zsmile.modules.generator.constant.DefaultConstants;
import top.zsmile.modules.generator.dao.GeneratorDao;
import top.zsmile.modules.generator.service.GeneratorSerivce;

import java.util.List;
import java.util.Map;

@Service("generatorService")
@DataSource("master")
public class GeneratorServiceImpl implements GeneratorSerivce {

    @Autowired
    private GeneratorDao generatorDao;

    @Override
    @DataSource(DefaultConstants.GENERATOR_DATASOURCE_KEY)
    public List<Map<String, Object>> queryTableList(String tableName) {
        return generatorDao.selectTableList(tableName);
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        return generatorDao.selectTable(tableName);
    }

    @Override
    public List<Map<String, Object>> queryTableColumns(String tableName) {
        return generatorDao.selectTableColumns(tableName);
    }
}
