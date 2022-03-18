package top.zsmile.common.validator;

import org.springframework.util.StringUtils;
import top.zsmile.common.utils.ValidatorUtils;
import top.zsmile.common.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public void initialize(Phone constraintAnnotation) {
        // 在这里获取一些注解上的值，比如message,require，设置为局部变量即可。

    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(phone)) {
            return true;
        }
        return ValidatorUtils.isPhone(phone);
    }
}
