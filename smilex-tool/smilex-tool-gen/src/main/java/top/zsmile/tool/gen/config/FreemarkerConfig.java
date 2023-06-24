package top.zsmile.tool.gen.config;

import freemarker.template.Configuration;
import freemarker.template.Version;
import org.springframework.core.io.ClassPathResource;
import top.zsmile.tool.gen.constant.DefaultConstants;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class FreemarkerConfig {
    public static final Configuration INSTANCE;

    static {
        INSTANCE = new Configuration(new Version("2.3.30"));
        try {
            INSTANCE.setDirectoryForTemplateLoading((new ClassPathResource("template/freemarker")).getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        INSTANCE.setDefaultEncoding(DefaultConstants.ENCODEING);
        INSTANCE.setURLEscapingCharset(DefaultConstants.ENCODEING);
        INSTANCE.setDateFormat(DefaultConstants.DATE_FORMAT);
        INSTANCE.setDateTimeFormat(DefaultConstants.DATETIME_FORMAT);
        INSTANCE.setTimeFormat(DefaultConstants.TIME_FORMAT);
        INSTANCE.setLocale(Locale.CHINA);

    }

}
