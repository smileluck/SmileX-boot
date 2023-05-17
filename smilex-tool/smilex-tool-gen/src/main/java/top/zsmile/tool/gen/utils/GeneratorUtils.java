package top.zsmile.tool.gen.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import top.zsmile.common.core.domain.ZipFileEntity;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.tool.gen.config.FreemakerConfig;
import top.zsmile.tool.gen.domain.entity.GeneratorEntity;
import top.zsmile.tool.gen.domain.model.TableModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GeneratorUtils {


    public static File generateByFtl(String savePath, String fileName, String templateName, TableModel obj) {
        File file = new File(savePath);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (!mkdirs) {
                throw new SXException("创建文件失败");
            }
        }
        Writer out = null;
        File saveFile = null;
        try {
            Template template = null;
            template = FreemakerConfig.INSTANCE.getTemplate(templateName);
            saveFile = new File(savePath + fileName);

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile), "UTF-8"));
            //FreeMarker使用Word模板和数据生成Word文档
            template.process(obj, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            throw new SXException(e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return saveFile;
    }

    /**
     * genZipCode
     *
     * @return
     */
    public static List<ZipFileEntity> genZipCode(String savePath,GeneratorEntity generatorEntity, List<TableModel> tableModels) {
        List<ZipFileEntity> zipFileEntities = new ArrayList<>();
        for (TableModel tableModel : tableModels) {
            // admin
            String basePath = savePath + "\\" + generatorEntity.getModuleName() + "\\java";
            File file = generateByFtl(basePath + "\\entity\\", tableModel.getBigHumpClass() + "Entity.java", "entity.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/entity", file));
            file = generateByFtl(basePath + "\\dao\\", tableModel.getBigHumpClass() + "Mapper.java", "mapper.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/dao", file));
            file = generateByFtl(basePath + "\\service\\", tableModel.getBigHumpClass() + "Service.java", "service.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/service", file));
            file = generateByFtl(basePath + "\\service\\impl\\", tableModel.getBigHumpClass() + "ServiceImpl.java", "serviceimpl.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/service/impl", file));
            file = generateByFtl(basePath + "\\controller\\", tableModel.getBigHumpClass() + "Controller.java", "controller.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/controller", file));
            file = generateByFtl(basePath + "\\mapper\\" + generatorEntity.getModuleName() + "\\", tableModel.getBigHumpClass() + ".xml", "mapperXml.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/mapper/" + generatorEntity.getModuleName(), file));

            // admin-vue
            basePath = savePath + "\\" + generatorEntity.getModuleName() + "\\vue\\" + generatorEntity.getModuleName() + "\\";
            file = generateByFtl(basePath, tableModel.getBigHumpClass() + ".vue", "vuePage.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/vue/" + generatorEntity.getModuleName(), file));
            file = generateByFtl(basePath + "modules\\", tableModel.getBigHumpClass() + "Model.vue", "vuePageModel.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/vue/" + generatorEntity.getModuleName() + "/modules", file));

            // menu-sql
            basePath = savePath + "\\" + generatorEntity.getModuleName() + "\\sql\\";
            file = generateByFtl(basePath, tableModel.getBigHumpClass() + ".sql", "menuSql.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/sql", file));
        }
        return zipFileEntities;
    }

    public static void genLocalCode(GeneratorEntity generatorEntity, List<TableModel> tableModels) {
        String savePath = generatorEntity.getSavePath();
        for (TableModel tableModel : tableModels) {
            // admin
            String basePath = savePath + "\\" + generatorEntity.getModuleName() + "\\java";
            generateByFtl(basePath + "\\entity\\", tableModel.getBigHumpClass() + "Entity.java", "entity.ftl", tableModel);
            generateByFtl(basePath + "\\dao\\", tableModel.getBigHumpClass() + "Mapper.java", "mapper.ftl", tableModel);
            generateByFtl(basePath + "\\service\\", tableModel.getBigHumpClass() + "Service.java", "service.ftl", tableModel);
            generateByFtl(basePath + "\\service\\impl\\", tableModel.getBigHumpClass() + "ServiceImpl.java", "serviceimpl.ftl", tableModel);
            generateByFtl(basePath + "\\controller\\", tableModel.getBigHumpClass() + "Controller.java", "controller.ftl", tableModel);
            generateByFtl(basePath + "\\mapper\\" + generatorEntity.getModuleName() + "\\", tableModel.getBigHumpClass() + ".xml", "mapperXml.ftl", tableModel);

            // admin-vue
            basePath = savePath + "\\" + generatorEntity.getModuleName() + "\\vue\\" + generatorEntity.getModuleName() + "\\";
            generateByFtl(basePath, tableModel.getBigHumpClass() + ".vue", "vuePage.ftl", tableModel);
            generateByFtl(basePath + "modules\\", tableModel.getBigHumpClass() + "Model.vue", "vuePageModel.ftl", tableModel);

            // menu-sql
            basePath = savePath + "\\" + generatorEntity.getModuleName() + "\\sql\\";
            generateByFtl(basePath, tableModel.getBigHumpClass() + ".sql", "menuSql.ftl", tableModel);
        }
    }
}
