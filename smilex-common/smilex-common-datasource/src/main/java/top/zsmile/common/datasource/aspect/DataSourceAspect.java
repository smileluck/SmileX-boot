package top.zsmile.common.datasource.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.zsmile.common.datasource.DataSourceContentHolder;
import top.zsmile.common.datasource.DynamicDataSource;
import top.zsmile.common.datasource.annotation.DS;
import top.zsmile.common.core.exception.SXException;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class DataSourceAspect {

    @Pointcut("@within(top.zsmile.common.datasource.annotation.DS) || @annotation(top.zsmile.common.datasource.annotation.DS)")
    public void dataSourceAspect() {

    }

    @Before("dataSourceAspect()")
    public void beforeSwitch(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DS methodDS = method.getAnnotation(DS.class);
        String value = null;
        if (methodDS != null) {
            value = methodDS.value();
        } else {
            Class<?> aClass = joinPoint.getTarget().getClass();
            DS annotation = aClass.getAnnotation(DS.class);
            if (annotation != null) {
                value = annotation.value();

            }
        }
        if (checkValue(value)) {
            DataSourceContentHolder.add(value);
        }
    }

    private boolean checkValue(final String value) {
        if (!StringUtils.isEmpty(value)) {
            boolean containKey = DynamicDataSource.getInstance().containKey(value);
            if (!containKey) {
                throw new SXException("数据源" + value + "未准备");
            }
        }
        return true;
    }

    @After("dataSourceAspect()")
    public void afterSwitchDS() {
        DataSourceContentHolder.poll();
    }
}
