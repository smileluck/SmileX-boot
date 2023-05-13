package top.zsmile.common.core.annotation;


import top.zsmile.common.core.validator.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {
    /**
     * 异常消息
     *
     * @return
     */
    String message() default "手机号格式不正确";

    /**
     * 分组
     *
     * @return
     */
    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};
}
