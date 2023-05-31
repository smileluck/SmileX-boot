package top.zsmile.common.datasource.annotation;

import org.springframework.transaction.annotation.Propagation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DSTransactional {

    Class<? extends Throwable>[] rollbackFor() default {Exception.class};

    Class<? extends Throwable>[] noRollbackFor() default {};

    Propagation propagation() default Propagation.REQUIRED;
}
