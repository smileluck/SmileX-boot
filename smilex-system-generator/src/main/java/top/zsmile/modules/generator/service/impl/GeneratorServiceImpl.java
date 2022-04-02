package top.zsmile.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zsmile.common.domain.ZipFileEntity;
import top.zsmile.core.datasource.annotation.DataSource;
import top.zsmile.modules.generator.constant.DefaultConstants;
import top.zsmile.modules.generator.convert.MysqlTypeConvert;
import top.zsmile.modules.generator.dao.GeneratorDao;
import top.zsmile.modules.generator.domain.entity.GeneratorEntity;
import top.zsmile.modules.generator.domain.model.ColumnModel;
import top.zsmile.modules.generator.domain.model.TableModel;
import top.zsmile.modules.generator.service.GeneratorSerivce;
import top.zsmile.utils.GeneratorUtils;
import top.zsmile.utils.NameStyleUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("generatorService")
@DataSource("master")
public class GeneratorServiceImpl implements GeneratorSerivce {

    @Autowired
    private GeneratorDao generatorDao;

    @Autowired
    private MysqlTypeConvert mysqlTypeConvert;

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
    public List<ColumnModel> queryTableColumns(String tableName) {
        return generatorDao.selectTableColumns(tableName);
    }

    @Override
    public List<ZipFileEntity> genCodeZip(GeneratorEntity generatorEntity) {
        List<ZipFileEntity> List = new ArrayList<>();
        String packagePath = generatorEntity.getPackagePath();
        String modules = generatorEntity.getModuleName();
        List<String> tableName = generatorEntity.getTableName();


        return null;
    }

    private ZipFileEntity genCodeFile(String tableName) {

        return null;
    }

    @Override
    public File genCodeLocal(GeneratorEntity generatorEntity) {
        genCodeModel(generatorEntity.getModuleName(), generatorEntity.getTableName());
//        GeneratorUtils.genCodeFiles("/generator", generatorEntity);
        return null;
    }

    /**
     * 加载表模型
     *
     * @param moduleName
     * @param tableNames
     * @return
     */
    private List<TableModel> genCodeModel(String moduleName, List<String> tableNames) {
        List<TableModel> tableModels = new ArrayList<>(tableNames.size());
        Iterator<String> iterator = tableNames.iterator();
        while (iterator.hasNext()) {
            String tableName = iterator.next();
            Map<String, String> tableMapInfo = generatorDao.selectTable(tableName);
            if (tableMapInfo == null || tableMapInfo.size() <= 0) {
                continue;
            }

            List<ColumnModel> columns = generatorDao.selectTableColumns(tableName);

            TableModel tableModel = new TableModel();

            Iterator<ColumnModel> iterator1 = columns.iterator();
            while (iterator1.hasNext()) {
                ColumnModel next = iterator1.next();
                String convert = mysqlTypeConvert.convert(next.getDataType());
                next.setConvertDataType(convert);
                next.setHumpColumnName(NameStyleUtils.lineToHump(next.getDataType(), false));
                if (next.getColumnKey().equalsIgnoreCase("PRI") && next.getColumnName().equalsIgnoreCase("id")) {
                    tableModel.setPrimaryColumn(next);
                    iterator1.remove();
                }
            }

            tableModel.setModuleName(moduleName);
            tableModel.setBigHumpClass(NameStyleUtils.lineToHump(tableName, true));
            tableModel.setSmallHumpClass(NameStyleUtils.lineToHump(tableName, false));
            tableModel.setTableName(tableName);
            tableModel.setTableComment(tableMapInfo.get("tableComment"));
            tableModel.setColumnModels(columns);
            tableModels.add(tableModel);
        }
        return tableModels;
    }

}
