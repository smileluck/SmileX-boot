package top.zsmile.tool.gen.config;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Version;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.tool.gen.constant.DefaultConstants;

import java.util.Locale;

/**
 * FreeMarker 单例配置
 * <p>
 * 使用 ClassTemplateLoader 从 classpath 加载模板（fat jar 内同样可用，
 * 原实现的 ClassPathResource#getFile() 在 jar 内会抛 FileNotFoundException）。
 */
public class FreemarkerConfig {
    public static final Configuration INSTANCE;

    static {
        INSTANCE = new Configuration(new Version("2.3.30"));
        try {
            INSTANCE.setTemplateLoader(new ClassTemplateLoader(FreemarkerConfig.class, "/template/freemarker"));
        } catch (Exception e) {
            throw new SXException("FreeMarker 模板目录初始化失败", e);
        }
        INSTANCE.setDefaultEncoding(DefaultConstants.ENCODING);
        INSTANCE.setURLEscapingCharset(DefaultConstants.ENCODING);
        INSTANCE.setDateFormat(DefaultConstants.DATE_FORMAT);
        INSTANCE.setDateTimeFormat(DefaultConstants.DATETIME_FORMAT);
        INSTANCE.setTimeFormat(DefaultConstants.TIME_FORMAT);
        INSTANCE.setLocale(Locale.CHINA);
    }
}
