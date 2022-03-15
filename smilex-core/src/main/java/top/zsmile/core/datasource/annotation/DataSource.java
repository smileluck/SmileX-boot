package top.zsmile.core.datasource.annotation;

import top.zsmile.core.datasource.DynamicDataSourceConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String value() default DynamicDataSourceConfig.MASTER;
}
