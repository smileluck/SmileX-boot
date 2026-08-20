package top.zsmile.tool.gen.utils;

import freemarker.template.Template;
import top.zsmile.common.core.domain.ZipFileEntity;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.tool.gen.constant.DefaultConstants;
import top.zsmile.tool.gen.config.FreemarkerConfig;
import top.zsmile.tool.gen.domain.entity.GeneratorEntity;
import top.zsmile.tool.gen.domain.model.TableModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 代码生成落盘/渲染工具
 * <p>
 * 路径全部使用 {@link File} 构造（跨平台，替代原硬编码 "\\"）；
 * 按 templateTypes 过滤生成物；支持渲染到字符串（预览）。
 */
public class GeneratorUtils {

    /**
     * 渲染模板到字符串（预览用，不落盘）
     */
    public static String renderToString(String templateName, TableModel obj) {
        try (StringWriter out = new StringWriter()) {
            Template template = FreemarkerConfig.INSTANCE.getTemplate(templateName);
            template.process(obj, out);
            return out.toString();
        } catch (Exception e) {
            throw new SXException("模板渲染失败: " + templateName + ", " + e.getMessage(), e);
        }
    }

    /**
     * 渲染模板并写入文件
     */
    public static File generateByFtl(File dir, String fileName, String templateName, TableModel obj) {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new SXException("创建目录失败: " + dir);
        }
        File saveFile = new File(dir, fileName);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile), StandardCharsets.UTF_8))) {
            Template template = FreemarkerConfig.INSTANCE.getTemplate(templateName);
            template.process(obj, out);
        } catch (Exception e) {
            throw new SXException("生成文件失败: " + saveFile + ", " + e.getMessage(), e);
        }
        return saveFile;
    }

    /**
     * 生成到临时目录（zip 打包前的暂存区）
     */
    public static List<ZipFileEntity> genZipCode(Path stagingRoot, GeneratorEntity generatorEntity, List<TableModel> tableModels) {
        List<ZipFileEntity> zipFileEntities = new ArrayList<>();
        Set<String> types = resolveTemplateTypes(generatorEntity);
        for (TableModel tableModel : tableModels) {
            File javaBase = stagingRoot.resolve(generatorEntity.getModuleName()).resolve("java").toFile();
            File vueBase = stagingRoot.resolve(generatorEntity.getModuleName()).resolve("vue").resolve(generatorEntity.getModuleName()).toFile();
            File sqlBase = stagingRoot.resolve(generatorEntity.getModuleName()).resolve("sql").toFile();
            String moduleName = generatorEntity.getModuleName();

            if (types.contains("entity")) {
                zipFileEntities.add(new ZipFileEntity("/entity",
                        generateByFtl(new File(javaBase, "entity"), tableModel.getBigHumpClass() + "Entity.java", "entity.ftl", tableModel)));
            }
            if (types.contains("mapper")) {
                zipFileEntities.add(new ZipFileEntity("/dao",
                        generateByFtl(new File(javaBase, "dao"), tableModel.getBigHumpClass() + "Mapper.java", "mapper.ftl", tableModel)));
            }
            if (types.contains("service")) {
                zipFileEntities.add(new ZipFileEntity("/service",
                        generateByFtl(new File(javaBase, "service"), tableModel.getBigHumpClass() + "Service.java", "service.ftl", tableModel)));
            }
            if (types.contains("serviceimpl")) {
                zipFileEntities.add(new ZipFileEntity("/service/impl",
                        generateByFtl(new File(javaBase, "service/impl"), tableModel.getBigHumpClass() + "ServiceImpl.java", "serviceimpl.ftl", tableModel)));
            }
            if (types.contains("controller")) {
                zipFileEntities.add(new ZipFileEntity("/controller",
                        generateByFtl(new File(javaBase, "controller"), tableModel.getBigHumpClass() + "Controller.java", "controller.ftl", tableModel)));
            }
            if (types.contains("xml")) {
                zipFileEntities.add(new ZipFileEntity("/mapper/" + moduleName,
                        generateByFtl(new File(javaBase, "mapper/" + moduleName), tableModel.getBigHumpClass() + ".xml", "mapperXml.ftl", tableModel)));
            }
            if (types.contains("vue")) {
                zipFileEntities.add(new ZipFileEntity("/vue/" + moduleName,
                        generateByFtl(vueBase, tableModel.getBigHumpClass() + ".vue", "vuePage.ftl", tableModel)));
            }
            if (types.contains("vuemodel")) {
                zipFileEntities.add(new ZipFileEntity("/vue/" + moduleName + "/modules",
                        generateByFtl(new File(vueBase, "modules"), tableModel.getBigHumpClass() + "Model.vue", "vuePageModel.ftl", tableModel)));
            }
            if (types.contains("sql")) {
                zipFileEntities.add(new ZipFileEntity("/sql",
                        generateByFtl(sqlBase, tableModel.getBigHumpClass() + ".sql", "menuSql.ftl", tableModel)));
            }
        }
        return zipFileEntities;
    }

    /**
     * 生成到本地目录
     */
    public static void genLocalCode(GeneratorEntity generatorEntity, List<TableModel> tableModels) {
        File saveRoot = new File(generatorEntity.getSavePath());
        Set<String> types = resolveTemplateTypes(generatorEntity);
        for (TableModel tableModel : tableModels) {
            File javaBase = new File(new File(saveRoot, generatorEntity.getModuleName()), "java");
            File vueBase = new File(new File(new File(new File(saveRoot, generatorEntity.getModuleName()), "vue"), generatorEntity.getModuleName()), "");
            File sqlBase = new File(new File(saveRoot, generatorEntity.getModuleName()), "sql");
            String moduleName = generatorEntity.getModuleName();

            if (types.contains("entity")) {
                generateByFtl(new File(javaBase, "entity"), tableModel.getBigHumpClass() + "Entity.java", "entity.ftl", tableModel);
            }
            if (types.contains("mapper")) {
                generateByFtl(new File(javaBase, "dao"), tableModel.getBigHumpClass() + "Mapper.java", "mapper.ftl", tableModel);
            }
            if (types.contains("service")) {
                generateByFtl(new File(javaBase, "service"), tableModel.getBigHumpClass() + "Service.java", "service.ftl", tableModel);
            }
            if (types.contains("serviceimpl")) {
                generateByFtl(new File(javaBase, "service/impl"), tableModel.getBigHumpClass() + "ServiceImpl.java", "serviceimpl.ftl", tableModel);
            }
            if (types.contains("controller")) {
                generateByFtl(new File(javaBase, "controller"), tableModel.getBigHumpClass() + "Controller.java", "controller.ftl", tableModel);
            }
            if (types.contains("xml")) {
                generateByFtl(new File(javaBase, "mapper/" + moduleName), tableModel.getBigHumpClass() + ".xml", "mapperXml.ftl", tableModel);
            }
            if (types.contains("vue")) {
                generateByFtl(vueBase, tableModel.getBigHumpClass() + ".vue", "vuePage.ftl", tableModel);
            }
            if (types.contains("vuemodel")) {
                generateByFtl(new File(vueBase, "modules"), tableModel.getBigHumpClass() + "Model.vue", "vuePageModel.ftl", tableModel);
            }
            if (types.contains("sql")) {
                generateByFtl(sqlBase, tableModel.getBigHumpClass() + ".sql", "menuSql.ftl", tableModel);
            }
        }
    }

    /**
     * 归一化模板类型选择（空=全部；未知类型 fail-fast）
     */
    public static Set<String> resolveTemplateTypes(GeneratorEntity generatorEntity) {
        List<String> requested = generatorEntity.getTemplateTypes();
        if (requested == null || requested.isEmpty()) {
            return new HashSet<>(DefaultConstants.DEFAULT_TEMPLATE_TYPES);
        }
        Set<String> result = new HashSet<>();
        for (String type : requested) {
            String t = type == null ? "" : type.trim();
            if (!DefaultConstants.TEMPLATE_TYPES.contains(t)) {
                throw new SXException("不支持的模板类型: " + t + "，可选: " + DefaultConstants.TEMPLATE_TYPES);
            }
            result.add(t);
        }
        return result;
    }

    /**
     * 递归删除目录/文件（zip 暂存区清理）
     */
    public static void deleteRecursively(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                deleteRecursively(child);
            }
        }
        if (!file.delete()) {
            file.deleteOnExit();
        }
    }
}
