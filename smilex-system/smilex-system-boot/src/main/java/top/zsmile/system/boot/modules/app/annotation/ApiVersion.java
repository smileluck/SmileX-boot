package top.zsmile.system.boot.modules.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API 版本控制
 * 允许 Class 和 Method 级别
 *
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/17/21:06
 * @ClassName: ApiVersion
 * @Description: ApiVersion
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {

    /**
     * @return 版本
     */
    int value() default 1;
}
