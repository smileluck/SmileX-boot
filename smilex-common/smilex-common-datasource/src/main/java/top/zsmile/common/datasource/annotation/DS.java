package top.zsmile.common.datasource.annotation;

import top.zsmile.common.datasource.DynamicDataSourceConfig;
import top.zsmile.common.datasource.properties.DynamicDataSourceProperties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DS {
    String value() default DynamicDataSourceProperties.PRIMARY;
}
