package top.zsmile.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import top.zsmile.common.domain.ZipFileEntity;
import top.zsmile.modules.generator.config.FreemakerConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

public class GeneratorUtils {


    public static File generateByFtl(String savePath, String templateName, Object obj) {
        File file = new File(savePath);
        try {
            Template template = null;
            template = FreemakerConfig.INSTANCE.getTemplate(templateName);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            //FreeMarker使用Word模板和数据生成Word文档
            template.process(obj, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * genZipCode
     *
     * @return
     */
    public static ZipFileEntity genCodeZip(String savePath, String templateName) {
        return null;
    }

    public static List<ZipFileEntity> genCodeFiles(String savePath, Object obj) {
        List<ZipFileEntity> zipFileEntities = new ArrayList<>();
        File file = generateByFtl(savePath, "entity.ftl", obj);
        zipFileEntities.add(new ZipFileEntity("/entity", file));
        return zipFileEntities;
    }
}
