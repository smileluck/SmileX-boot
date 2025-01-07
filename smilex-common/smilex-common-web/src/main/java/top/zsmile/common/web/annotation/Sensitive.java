package top.zsmile.common.web.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.zsmile.common.web.enums.SensitizeType;
import top.zsmile.common.web.serializer.SensitizeSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitizeSerializer.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sensitive {


    SensitizeType type() default SensitizeType.DEFAULT;


    int startInclude() default 0;


    int endExclude() default 0;
}
