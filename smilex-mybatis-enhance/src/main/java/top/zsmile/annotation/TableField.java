package top.zsmile.annotation;

import jdk.nashorn.internal.ir.annotations.Reference;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableField {
    /**
     * 是否存在
     *
     * @return
     */
    boolean exist() default true;
}
