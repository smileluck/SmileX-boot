package top.zsmile.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import top.zsmile.core.api.R;
import top.zsmile.modules.generator.config.FreemakerConfig;

import java.io.*;
import java.util.HashMap;

public class GeneratorUtils {
    public static File generateEntity(String savePath) {
        return generateByFtl("entity.ftl", savePath);
    }

    public static File generateController(String savePath) {
        return generateByFtl("controller.ftl", savePath);
    }

    public static File generateDao(String savePath) {
        return generateByFtl("dao.ftl", savePath);
    }

    public static File generateDaoXml(String savePath) {
        return generateByFtl("dao-xml.ftl", savePath);
    }

    public static File generateService(String savePath) {
        return generateByFtl("service.ftl", savePath);
    }

    public static File generateServiceImpl(String savePath) {
        return generateByFtl("serviceImpl.ftl", savePath);
    }

    public static File generateByFtl(String templateName, String savePath) {
        File file = new File(savePath);
        try {
            Template template = null;
            template = FreemakerConfig.INSTANCE.getTemplate(templateName);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            //FreeMarker使用Word模板和数据生成Word文档
            template.process(new HashMap<>(), out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return file;
    }
}
