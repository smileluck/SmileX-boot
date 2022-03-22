package top.zsmile.modules.generator.service.impl;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zsmile.common.entity.ZipFile;
import top.zsmile.core.datasource.annotation.DataSource;
import top.zsmile.modules.generator.constant.DefaultConstants;
import top.zsmile.modules.generator.dao.GeneratorDao;
import top.zsmile.modules.generator.entity.GeneratorEntity;
import top.zsmile.modules.generator.service.GeneratorSerivce;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
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

    @Override
    public List<ZipFile> genCodeZip(GeneratorEntity generatorEntity) {
        List<ZipFile> List = new ArrayList<>();
        String packagePath = generatorEntity.getPackagePath();
        String modules = generatorEntity.getModuleName();
        List<String> tableName = generatorEntity.getTableName();


        return null;
    }

    private ZipFile genCodeFile(String tableName) {
        return null;
    }
}
