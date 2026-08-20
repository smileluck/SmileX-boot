package top.zsmile.tool.gen.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.zsmile.common.core.constant.StringConstant;
import top.zsmile.common.core.domain.ZipFileEntity;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.common.core.utils.uuid.SnowFlake;
import top.zsmile.common.core.utils.file.ZipUtils;
import top.zsmile.common.datasource.DataSourceFactory;
import top.zsmile.common.datasource.annotation.DS;
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
import top.zsmile.tool.gen.service.GeneratorService;
import top.zsmile.tool.gen.utils.GeneratorUtils;
import top.zsmile.tool.gen.utils.NameStyleUtils;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static top.zsmile.tool.gen.constant.DefaultConstants.DEFAULT_DELETE_LOGIC_KEY;

@Slf4j
@Service("generatorService")
@DS("master")
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    private GeneratorDao generatorDao;

    @Autowired
    private MysqlTypeConvert mysqlTypeConvert;

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Value("${smilex.generator.worker-id:2}")
    private long workerId;

    @Value("${smilex.generator.datacenter-id:2}")
    private long dataCenterId;

    /**
     * 本地生成的根目录白名单（空=不限制，但始终拒绝 .. 越界）
     */
    @Value("${smilex.generator.save-root:}")
    private String saveRoot;

    private volatile SnowFlake snowFlake;

    private SnowFlake getSnowFlake() {
        if (snowFlake == null) {
            synchronized (this) {
                if (snowFlake == null) {
                    snowFlake = new SnowFlake(workerId, dataCenterId);
                }
            }
        }
        return snowFlake;
    }

    @Override
    public List<Map<String, Object>> queryTableList(String tableName) {
        return generatorDao.selectTableList(tableName);
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        checkTableName(tableName);
        return generatorDao.selectTable(tableName);
    }

    @Override
    public List<ColumnModel> queryTableColumns(String tableName) {
        checkTableName(tableName);
        return generatorDao.selectTableColumns(tableName);
    }

    @Override
    public File genZipCode(GeneratorEntity generatorEntity) {
        validate(generatorEntity);
        List<TableModel> tableModels = genCodeModel(generatorEntity);
        Path staging;
        try {
            staging = Files.createTempDirectory("smilex-gen-");
        } catch (Exception e) {
            throw new SXException("创建临时目录失败", e);
        }
        try {
            List<ZipFileEntity> zipFileEntities = GeneratorUtils.genZipCode(staging, generatorEntity, tableModels);
            File zip = ZipUtils.createZip(staging.resolve("code-" + System.nanoTime() + ".zip").toString(), "/", zipFileEntities);
            return zip;
        } finally {
            // 清理暂存目录中的源文件（zip 已独立生成）
            File[] children = staging.toFile().listFiles();
            if (children != null) {
                for (File child : children) {
                    if (child.isDirectory()) {
                        GeneratorUtils.deleteRecursively(child);
                    }
                }
            }
        }
    }

    @Override
    public void genLocalCode(GeneratorEntity generatorEntity) {
        validate(generatorEntity);
        List<TableModel> tableModels = genCodeModel(generatorEntity);
        GeneratorUtils.genLocalCode(generatorEntity, tableModels);
    }

    @Override
    public String previewCode(GeneratorEntity generatorEntity, String templateType) {
        validate(generatorEntity);
        String ftl = templateFtl(templateType);
        List<TableModel> tableModels = genCodeModel(generatorEntity);
        if (tableModels.isEmpty()) {
            throw new SXException("未找到可生成的表");
        }
        return GeneratorUtils.renderToString(ftl, tableModels.get(0));
    }

    @Override
    public void switchDs(DatabaseConnEntity databaseConnEntity) {
        // 类型白名单
        String type = databaseConnEntity.getType() == null ? "" : databaseConnEntity.getType().trim().toLowerCase();
        if (!DefaultConstants.SUPPORTED_DB_TYPES.contains(type)) {
            throw new SXException("不支持的数据库类型: " + type + "，当前仅支持: " + DefaultConstants.SUPPORTED_DB_TYPES);
        }
        // 地址白名单（主机名/IP 字符集，杀 SSRF 面）
        if (databaseConnEntity.getAddress() == null
                || !DefaultConstants.DB_ADDRESS_PATTERN.matcher(databaseConnEntity.getAddress()).matches()) {
            throw new SXException("连接地址非法（仅允许主机名/IP 字符）");
        }
        if (databaseConnEntity.getPort() == null || databaseConnEntity.getPort() <= 0 || databaseConnEntity.getPort() > 65535) {
            throw new SXException("端口需在1~65535之间");
        }
        // 参数黑名单过滤
        String params = filterDangerousParams(databaseConnEntity.getParams());
        databaseConnEntity.setParams(params);

        String url = databaseConnEntity.getUrl();
        // 先试探连接：失败不替换原数据源（防 DoS：原实现会先 close master 再失败）
        try (Connection ignored = DriverManager.getConnection(url, databaseConnEntity.getUsername(), databaseConnEntity.getPassword())) {
            log.info("生成器数据源试探连接成功: {}:{}", databaseConnEntity.getAddress(), databaseConnEntity.getPort());
        } catch (Exception e) {
            throw new SXException("数据库连接失败: " + e.getMessage());
        }
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUsername(databaseConnEntity.getUsername());
        dataSourceProperties.setPassword(databaseConnEntity.getPassword());
        dataSourceProperties.setUrl(url);
        try {
            DruidDataSource dataSource = DataSourceFactory.createDataSource(dynamicDataSourceProperties.getDruid(), dataSourceProperties);
            DynamicDataSource.getInstance().replace(DynamicDataSourceProperties.PRIMARY, dataSource);
        } catch (SQLException e) {
            log.error("数据库切换失败", e);
            throw new SXException("数据库切换失败: " + e.getMessage());
        }
    }

    // ==================== 校验 ====================

    private void checkTableName(String tableName) {
        if (tableName == null || !DefaultConstants.TABLE_NAME_PATTERN.matcher(tableName).matches()) {
            throw new SXException("非法的表名: [" + tableName + "]");
        }
    }

    private void validate(GeneratorEntity generatorEntity) {
        if (!DefaultConstants.MODULE_NAME_PATTERN.matcher(generatorEntity.getModuleName().trim()).matches()) {
            throw new SXException("模块名仅允许字母数字下划线中划线");
        }
        for (String name : generatorEntity.getTableName()) {
            checkTableName(name);
        }
        // 模板类型合法性（空=全部）
        GeneratorUtils.resolveTemplateTypes(generatorEntity);
        if (generatorEntity.getSavePath() != null) {
            checkSavePath(generatorEntity.getSavePath());
        }
    }

    /**
     * savePath 归一化校验：拒绝 .. 越界；配置了 save-root 时限定根目录内
     */
    private void checkSavePath(String savePath) {
        try {
            File root = new File(savePath).getCanonicalFile();
            if (root.getAbsolutePath().contains("..")) {
                throw new SXException("保存路径非法");
            }
            if (saveRoot != null && !saveRoot.trim().isEmpty()) {
                File allowed = new File(saveRoot.trim()).getCanonicalFile();
                if (!root.toPath().startsWith(allowed.toPath())) {
                    throw new SXException("保存路径超出允许的根目录: " + allowed.getAbsolutePath());
                }
            }
        } catch (SXException e) {
            throw e;
        } catch (Exception e) {
            throw new SXException("保存路径非法: " + savePath);
        }
    }

    private String filterDangerousParams(String params) {
        if (params == null || params.isEmpty()) {
            return params;
        }
        StringBuilder safe = new StringBuilder();
        for (String pair : params.split("&")) {
            String key = pair.contains("=") ? pair.substring(0, pair.indexOf('=')) : pair;
            if (DefaultConstants.DANGEROUS_JDBC_PARAMS.contains(key)) {
                log.warn("已过滤危险 JDBC 参数: {}", key);
                continue;
            }
            if (safe.length() > 0) {
                safe.append("&");
            }
            safe.append(pair);
        }
        return safe.toString();
    }

    /**
     * 模板类型 -> 模板文件名
     */
    private String templateFtl(String templateType) {
        if (templateType == null || !DefaultConstants.TEMPLATE_TYPES.contains(templateType)) {
            throw new SXException("不支持的模板类型: " + templateType + "，可选: " + DefaultConstants.TEMPLATE_TYPES);
        }
        switch (templateType) {
            case "entity":
                return "entity.ftl";
            case "mapper":
                return "mapper.ftl";
            case "service":
                return "service.ftl";
            case "serviceimpl":
                return "serviceimpl.ftl";
            case "controller":
                return "controller.ftl";
            case "xml":
                return "mapperXml.ftl";
            case "vue":
                return "vuePage.ftl";
            case "vuemodel":
                return "vuePageModel.ftl";
            case "sql":
                return "menuSql.ftl";
            default:
                throw new SXException("不支持的模板类型: " + templateType);
        }
    }

    // ==================== 模型组装 ====================

    /**
     * 加载表模型：主键 fail-fast（无主键/复合主键明确报错）、注释清洗、排除列、import 类型收集
     */
    private List<TableModel> genCodeModel(GeneratorEntity generatorEntity) {
        List<String> tableNames = generatorEntity.getTableName();
        String moduleName = generatorEntity.getModuleName().trim();
        String packages = generatorEntity.getPackagePath().trim();
        Set<String> excludeColumns = normalizeExcludes(generatorEntity.getExcludeColumns());

        List<TableModel> tableModels = new ArrayList<>(tableNames.size());
        for (String tableName : tableNames) {
            Map<String, String> tableMapInfo = generatorDao.selectTable(tableName);
            if (tableMapInfo == null || tableMapInfo.isEmpty()) {
                throw new SXException("查询不到表结构: " + tableName);
            }

            List<ColumnModel> columns = generatorDao.selectTableColumns(tableName);
            TableModel tableModel = new TableModel();
            ColumnModel primary = null;
            int priCount = 0;
            Set<String> importTypes = new HashSet<>();

            Iterator<ColumnModel> iterator = columns.iterator();
            while (iterator.hasNext()) {
                ColumnModel next = iterator.next();
                // 审计四字段由 BaseEntity 提供
                if (DefaultConstants.IGNORE_COLUMN.contains(next.getColumnName())) {
                    iterator.remove();
                    continue;
                }
                // 排除列（支持下划线/驼峰两种写法）
                if (excludeColumns.contains(next.getColumnName())
                        || excludeColumns.contains(NameStyleUtils.lineToHump(next.getColumnName(), false))) {
                    iterator.remove();
                    continue;
                }
                next.setConvertDataType(mysqlTypeConvert.convert(next.getDataType(), next.getColumnType()));
                collectImportType(importTypes, next.getConvertDataType());
                next.setHumpColumnName(NameStyleUtils.lineToHump(next.getColumnName(), false));
                next.setBigHumpColumnName(NameStyleUtils.lineToHump(next.getColumnName(), true));
                next.setColumnComment(cleanComment(next.getColumnComment()));
                if ("PRI".equalsIgnoreCase(next.getColumnKey())) {
                    priCount++;
                    primary = next;
                    continue;
                }
                if (next.getColumnName().equalsIgnoreCase(DEFAULT_DELETE_LOGIC_KEY)) {
                    tableModel.setLogicDelColumn(next);
                    iterator.remove();
                    continue;
                }
                // 包含字典项目时，记录表模型中
                if ("enableFlag".equals(next.getHumpColumnName())) {
                    tableModel.setHasDict(Boolean.TRUE);
                }
            }

            if (priCount == 0) {
                throw new SXException("表[" + tableName + "]没有主键，无法生成 CRUD 代码");
            }
            if (priCount > 1) {
                throw new SXException("表[" + tableName + "]为复合主键，暂不支持生成（请先调整为单列主键）");
            }
            tableModel.setPrimaryColumn(primary);
            // 主键从普通列中移除（上面 continue 跳过了主键的普通列处理，此处剔除）
            columns.removeIf(column -> "PRI".equalsIgnoreCase(column.getColumnKey()));
            tableModel.setImportTypes(importTypes);

            tableModel.setPackages(packages);
            tableModel.setModuleName(moduleName);
            tableModel.setBigHumpClass(NameStyleUtils.lineToHump(tableName, true));
            tableModel.setSmallDashName(NameStyleUtils.lineToDash(tableName));
            tableModel.setSmallColonName(NameStyleUtils.lineToCustomStr(tableName, StringConstant.COLON));
            tableModel.setSmallHumpClass(NameStyleUtils.lineToHump(tableName, false));
            tableModel.setReqMapping(NameStyleUtils.lineToSlash(tableName));
            tableModel.setTableName(tableName);
            tableModel.setTableComment(cleanComment(tableMapInfo.get("tableComment")));
            tableModel.setColumnModels(columns);
            String[] filterColumn = {"password", "salt"};
            tableModel.setFilterColumn(filterColumn);
            MenuModel menuModel = new MenuModel();
            menuModel.setParentId(getSnowFlake().nextId());
            menuModel.setMenuIds(Arrays.asList(getSnowFlake().nextId(), getSnowFlake().nextId(),
                    getSnowFlake().nextId(), getSnowFlake().nextId()));
            tableModel.setMenuModel(menuModel);
            tableModels.add(tableModel);
        }
        return tableModels;
    }

    private Set<String> normalizeExcludes(List<String> excludeColumns) {
        Set<String> result = new HashSet<>();
        if (excludeColumns != null) {
            for (String column : excludeColumns) {
                if (column != null && !column.trim().isEmpty()) {
                    result.add(column.trim());
                }
            }
        }
        return result;
    }

    /**
     * 注释清洗：null 兜底空串、去换行、规避 Java 块注释截断
     */
    private String cleanComment(String comment) {
        if (comment == null) {
            return "";
        }
        return comment.replace("\r", " ").replace("\n", " ").replace("*/", "*∕").trim();
    }

    private void collectImportType(Set<String> importTypes, String javaType) {
        if ("LocalDate".equals(javaType) || "LocalDateTime".equals(javaType) || "LocalTime".equals(javaType)) {
            importTypes.add(javaType);
        } else if ("BigDecimal".equals(javaType)) {
            importTypes.add(javaType);
        }
    }
}
