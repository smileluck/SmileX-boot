package top.zsmile.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import top.zsmile.common.domain.ZipFileEntity;
import top.zsmile.core.exception.SXException;
import top.zsmile.modules.generator.config.FreemakerConfig;
import top.zsmile.modules.generator.config.GeneratorConfig;
import top.zsmile.modules.generator.domain.entity.GeneratorEntity;
import top.zsmile.modules.generator.domain.model.TableModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

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
    public static ZipFileEntity genCodeZip(String savePath, String templateName) {
        return null;
    }

    public static List<ZipFileEntity> genCodeFiles(GeneratorEntity generatorEntity, List<TableModel> tableModels) {
        List<ZipFileEntity> zipFileEntities = new ArrayList<>();
        for (TableModel tableModel : tableModels) {
            // admin
            File file = generateByFtl(generatorEntity.getSavePath() + "\\" + generatorEntity.getModuleName() + "\\entity\\", tableModel.getBigHumpClass() + "Entity.java", "entity.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/entity", file));
            file = generateByFtl(generatorEntity.getSavePath() + "\\" + generatorEntity.getModuleName() + "\\dao\\", tableModel.getBigHumpClass() + "Mapper.java", "mapper.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/dao", file));
            file = generateByFtl(generatorEntity.getSavePath() + "\\" + generatorEntity.getModuleName() + "\\service\\", tableModel.getBigHumpClass() + "Service.java", "service.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/service", file));
            file = generateByFtl(generatorEntity.getSavePath() + "\\" + generatorEntity.getModuleName() + "\\service\\impl\\", tableModel.getBigHumpClass() + "ServiceImpl.java", "serviceimpl.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/service/impl", file));
            file = generateByFtl(generatorEntity.getSavePath() + "\\" + generatorEntity.getModuleName() + "\\controller\\", tableModel.getBigHumpClass() + "Controller.java", "controller.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/controller", file));
            file = generateByFtl(generatorEntity.getSavePath() + "\\" + generatorEntity.getModuleName() + "\\mapper\\" + generatorEntity.getModuleName() + "\\", tableModel.getBigHumpClass() + ".xml", "mapperXml.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/mapper/" + generatorEntity.getModuleName(), file));

            // admin-vue
            file = generateByFtl(generatorEntity.getSavePath() + "\\vue\\" + generatorEntity.getModuleName() + "\\", tableModel.getBigHumpClass() + ".vue", "vuePage.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/vue/" + generatorEntity.getModuleName(), file));
            file = generateByFtl(generatorEntity.getSavePath() + "\\vue\\" + generatorEntity.getModuleName() + "\\modules\\", tableModel.getBigHumpClass() + "Model.vue", "vuePageModel.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/vue/" + generatorEntity.getModuleName() + "/modules", file));

            // menu-sql
            file = generateByFtl(generatorEntity.getSavePath() + "\\sql\\", tableModel.getBigHumpClass() + ".sql", "menuSql.ftl", tableModel);
            zipFileEntities.add(new ZipFileEntity("/sql", file));

        }
        return zipFileEntities;
    }
}
