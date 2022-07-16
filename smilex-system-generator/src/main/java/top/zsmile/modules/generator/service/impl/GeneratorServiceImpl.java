package top.zsmile.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zsmile.common.domain.ZipFileEntity;
import top.zsmile.common.utils.SnowFlake;
import top.zsmile.common.utils.StringPool;
import top.zsmile.common.utils.file.ZipUtils;
import top.zsmile.core.datasource.annotation.DataSource;
import top.zsmile.core.utils.SpringContextUtils;
import top.zsmile.modules.generator.constant.DefaultConstants;
import top.zsmile.modules.generator.convert.MysqlTypeConvert;
import top.zsmile.modules.generator.dao.GeneratorDao;
import top.zsmile.modules.generator.domain.entity.GeneratorEntity;
import top.zsmile.modules.generator.domain.model.ColumnModel;
import top.zsmile.modules.generator.domain.model.MenuModel;
import top.zsmile.modules.generator.domain.model.TableModel;
import top.zsmile.modules.generator.service.GeneratorSerivce;
import top.zsmile.utils.GeneratorUtils;
import top.zsmile.utils.NameStyleUtils;

import java.io.File;
import java.util.*;

import static top.zsmile.modules.generator.constant.DefaultConstants.DEFAULT_DELETE_LOGIC_KEY;

@Service("generatorService")
@DataSource("master")
public class GeneratorServiceImpl implements GeneratorSerivce {

    @Autowired
    private GeneratorDao generatorDao;

    @Autowired
    private MysqlTypeConvert mysqlTypeConvert;

    private SnowFlake snowFlake = new SnowFlake(1, 1);

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
    public File genZipCode(GeneratorEntity generatorEntity) {
        List<TableModel> tableModels = genCodeModel(generatorEntity);
        String contextPath = SpringContextUtils.getHttpServletRequest().getContextPath();
        List<ZipFileEntity> zipFileEntities = GeneratorUtils.genZipCode(contextPath, generatorEntity, tableModels);
        File zip = ZipUtils.createZip(contextPath + "/" + System.currentTimeMillis() + ".zip", "/", zipFileEntities);
        return zip;
    }

    @Override
    public void genLocalCode(GeneratorEntity generatorEntity) {
        List<TableModel> tableModels = genCodeModel(generatorEntity);
        GeneratorUtils.genLocalCode(generatorEntity, tableModels);
    }

    /**
     * 加载表模型
     *
     * @param generatorEntity
     * @return
     */
    private List<TableModel> genCodeModel(GeneratorEntity generatorEntity) {
        List<String> tableNames = generatorEntity.getTableName();

        String moduleName = generatorEntity.getModuleName().trim();
        String packages = generatorEntity.getPackagePath().trim();
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
                if (DefaultConstants.IGNORE_COLUMN.contains(next.getColumnName())) {
                    iterator1.remove();
                    continue;
                }
                String convert = mysqlTypeConvert.convert(next.getDataType());
                next.setConvertDataType(convert);
                next.setHumpColumnName(NameStyleUtils.lineToHump(next.getColumnName(), false));
                next.setBigHumpColumnName(NameStyleUtils.lineToHump(next.getColumnName(), true));
                if (next.getColumnKey().equalsIgnoreCase("PRI") && next.getColumnName().equalsIgnoreCase("id")) {
                    tableModel.setPrimaryColumn(next);
                    iterator1.remove();
                } else if (next.getColumnName().equalsIgnoreCase(DEFAULT_DELETE_LOGIC_KEY)) {
                    tableModel.setLogicDelColumn(next);
                    iterator1.remove();
                }
            }
            tableModel.setPackages(packages);
            tableModel.setModuleName(moduleName);
            tableModel.setBigHumpClass(NameStyleUtils.lineToHump(tableName, true));
            tableModel.setSmallDashName(NameStyleUtils.lineToDash(tableName));
            tableModel.setSmallColonName(NameStyleUtils.lineToCustomStr(tableName, StringPool.COLON));
            tableModel.setSmallHumpClass(NameStyleUtils.lineToHump(tableName, false));
            tableModel.setReqMapping(NameStyleUtils.lineToSlash(tableName));
            tableModel.setTableName(tableName);
            tableModel.setTableComment(tableMapInfo.get("tableComment"));
            tableModel.setColumnModels(columns);
            String[] filterColumn = {"password", "salt"};
            tableModel.setFilterColumn(filterColumn);
            MenuModel menuModel = new MenuModel();
            menuModel.setParentId(snowFlake.nextId());
            menuModel.setMenuIds(Arrays.asList(snowFlake.nextId(), snowFlake.nextId(), snowFlake.nextId(), snowFlake.nextId(), snowFlake.nextId()));
            tableModel.setMenuModel(menuModel);
            tableModels.add(tableModel);
        }
        return tableModels;
    }

}
