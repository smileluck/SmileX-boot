package top.zsmile.tool.gen.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zsmile.common.core.constant.StringConstant;
import top.zsmile.common.core.domain.ZipFileEntity;
import top.zsmile.common.core.utils.SnowFlake;
import top.zsmile.common.core.utils.file.ZipUtils;
import top.zsmile.common.datasource.DataSourceFactory;
import top.zsmile.common.datasource.annotation.DS;
import top.zsmile.common.core.utils.SpringContextUtils;
import top.zsmile.common.datasource.ds.DynamicDataSource;
import top.zsmile.common.datasource.properties.DataSourceProperties;
import top.zsmile.common.datasource.properties.DynamicDataSourceProperties;
import top.zsmile.tool.gen.constant.DefaultConstants;
import top.zsmile.tool.gen.convert.MysqlTypeConvert;
import top.zsmile.tool.gen.dao.GeneratorDao;
import top.zsmile.tool.gen.domain.entity.DatabaseConnEntity;
import top.zsmile.tool.gen.domain.entity.GeneratorEntity;
import top.zsmile.tool.gen.domain.model.ColumnModel;
import top.zsmile.tool.gen.domain.model.MenuModel;
import top.zsmile.tool.gen.domain.model.TableModel;
import top.zsmile.tool.gen.service.GeneratorSerivce;
import top.zsmile.tool.gen.utils.GeneratorUtils;
import top.zsmile.tool.gen.utils.NameStyleUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

import static top.zsmile.tool.gen.constant.DefaultConstants.DEFAULT_DELETE_LOGIC_KEY;

@Service("generatorService")
@DS("master")
public class GeneratorServiceImpl implements GeneratorSerivce {

    @Autowired
    private GeneratorDao generatorDao;

    @Autowired
    private MysqlTypeConvert mysqlTypeConvert;

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;


    private SnowFlake snowFlake = new SnowFlake(1, 1);

    @Override
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

    @Override
    public void switchDs(DatabaseConnEntity databaseConnEntity) {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUsername(databaseConnEntity.getUsername());
        dataSourceProperties.setPassword(databaseConnEntity.getPassword());
        dataSourceProperties.setUrl(databaseConnEntity.getUrl());
        DruidDataSource dataSource = DataSourceFactory.createDataSource(dynamicDataSourceProperties.getDruid(), dataSourceProperties);
        DynamicDataSource.getInstance().replace(DynamicDataSourceProperties.PRIMARY, dataSource);
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

                // 包含字典项目时，记录表模型中
                if (next.getHumpColumnName().equals("enableFlag")) {
                    tableModel.setHasDict(Boolean.TRUE);
                }
            }
            tableModel.setPackages(packages);
            tableModel.setModuleName(moduleName);
            tableModel.setBigHumpClass(NameStyleUtils.lineToHump(tableName, true));
            tableModel.setSmallDashName(NameStyleUtils.lineToDash(tableName));
            tableModel.setSmallColonName(NameStyleUtils.lineToCustomStr(tableName, StringConstant.COLON));
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
