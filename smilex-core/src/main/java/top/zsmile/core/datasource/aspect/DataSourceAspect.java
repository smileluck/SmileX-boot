package top.zsmile.core.datasource.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zsmile.core.datasource.DataSourceContentHolder;

@Aspect
@Component
@Slf4j
public class DataSourceAspect {

    @Before("@annotation(DataSource)")
    public void beforeSwitch(JoinPoint joinPoint) {
        Class<?> aClass = joinPoint.getTarget().getClass();
    }

    @After("@annotation(DataSource)")
    public void afterSwitchDS() {
        DataSourceContentHolder.clear();
    }
}
