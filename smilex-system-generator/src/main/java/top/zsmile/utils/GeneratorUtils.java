package top.zsmile.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import top.zsmile.core.api.R;
import top.zsmile.modules.generator.config.FreemakerConfig;

import java.io.*;
import java.util.HashMap;

public class GeneratorUtils {
    public static R generatorByFtl() {
        try {
            Template template = null;
            template = FreemakerConfig.INSTANCE.getTemplate("entity.ftl");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/test.docx")), "UTF-8"));
            //FreeMarker使用Word模板和数据生成Word文档
            template.process(new HashMap<>(), out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return R.success("生成成功");
    }
}
