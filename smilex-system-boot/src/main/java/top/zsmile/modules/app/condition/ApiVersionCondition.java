package top.zsmile.modules.app.condition;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/04/17/21:23
 * @ClassName: ApiVersionCondition
 * @Description: ApiVersionCondition
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    @Override
    public ApiVersionCondition combine(ApiVersionCondition apiVersionCondition) {
        return null;
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition apiVersionCondition, HttpServletRequest httpServletRequest) {
        return 0;
    }
}
