package top.zsmile.common.mybatis.datascope.aspect;

import top.zsmile.common.mybatis.datascope.DataScopeContentHolder;
import top.zsmile.common.mybatis.datascope.DataScopeFactory;
import top.zsmile.common.mybatis.datascope.DataScopePerm;
import top.zsmile.common.mybatis.datascope.annotation.DataScope;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/02/24/15:05
 * @ClassName: DataScopeAspect
 * @Description: DataScopeAspect
 */
@Aspect
@Slf4j
@Component
public class DataScopeAspect {

    @Pointcut("@within(top.zsmile.common.mybatis.datascope.annotation.DataScope) || @annotation(top.zsmile.common.mybatis.datascope.annotation.DataScope)")
    public void DataScopeAspect() {

    }

    @Before("DataScopeAspect()")
    public void beforeSwitch(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DataScope methodDataSource = method.getAnnotation(DataScope.class);
        DataScopePerm dataScopePerm = null;
        if (methodDataSource != null) {
            dataScopePerm = DataScopeFactory.create(methodDataSource);
        } else {
            Class<?> aClass = joinPoint.getTarget().getClass();
            DataScope annotation = aClass.getAnnotation(DataScope.class);
            if (annotation != null) {
                dataScopePerm = DataScopeFactory.create(annotation);
            }
        }
        DataScopeContentHolder.add(dataScopePerm);
    }


    @After("DataScopeAspect()")
    public void after() {
        DataScopeContentHolder.poll();
    }
}
