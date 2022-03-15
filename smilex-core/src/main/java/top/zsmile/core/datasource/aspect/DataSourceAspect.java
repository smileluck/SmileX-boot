package top.zsmile.core.datasource.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.zsmile.core.datasource.DataSourceContentHolder;
import top.zsmile.core.datasource.DynamicDataSource;
import top.zsmile.core.datasource.annotation.DataSource;
import top.zsmile.core.exception.SXException;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class DataSourceAspect {

    @Pointcut("@within(top.zsmile.core.datasource.annotation.DataSource) || @annotation(top.zsmile.core.datasource.annotation.DataSource)")
    public void dataSourceAspect() {

    }

    @Before("dataSourceAspect()")
    public void beforeSwitch(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DataSource methodDataSource = method.getAnnotation(DataSource.class);
        String value = null;
        if (methodDataSource != null) {
            value = methodDataSource.value();
        } else {
            Class<?> aClass = joinPoint.getTarget().getClass();
            DataSource annotation = aClass.getAnnotation(DataSource.class);
            if (annotation != null) {
                value = annotation.value();

            }
        }
        if (checkValue(value)) {
            DataSourceContentHolder.setDataSource(value);
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
        DataSourceContentHolder.clear();
    }
}
