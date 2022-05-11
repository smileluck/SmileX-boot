package top.zsmile.annotation;

import java.lang.annotation.*;

/**
 * 主键ID
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableId {

}
