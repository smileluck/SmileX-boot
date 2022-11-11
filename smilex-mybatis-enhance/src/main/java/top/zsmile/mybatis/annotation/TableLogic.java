package top.zsmile.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 逻辑删除标签
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableLogic {
}
