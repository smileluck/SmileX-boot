package top.zsmile.system.modules.generator.config;

import freemarker.template.Configuration;
import freemarker.template.Version;
import top.zsmile.system.modules.generator.constant.DefaultConstants;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class FreemakerConfig {
    public static final Configuration INSTANCE;

    static {
        INSTANCE = new Configuration(new Version("2.3.30"));
        try {
            String path = FreemakerConfig.class.getResource("/").getPath();
            INSTANCE.setDirectoryForTemplateLoading(new File(path + "/template/freemarker/"));
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
