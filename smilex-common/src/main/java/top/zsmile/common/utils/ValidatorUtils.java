package top.zsmile.common.utils;

import org.springframework.util.StringUtils;
import top.zsmile.core.exception.SXException;
import top.zsmile.core.utils.SpringContextUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtils {

    private final static Validator validator;

    static {
        // 这里可以使用 Factory工厂，也可使用Spring容器里的Bean对象。
        if (SpringContextUtils.getBean(Validator.class) != null) {
            validator = SpringContextUtils.getBean(Validator.class);
        } else {
            validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
    }

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^(13[0-9]|14[5|7|9]|15[0|1|2|3|5|6|7|8|9]|17[0|1|6|7|8]|18[0-9])\\d{8}$");

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws SXException 校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws SXException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(constraint.getMessage()).append("\r\n");
            }
            throw new SXException(msg.toString());
        }
    }

    public static boolean isPhone(String src) {
        if (StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher matcher = MOBILE_PATTERN.matcher(src);
        return matcher.matches();
    }
}
