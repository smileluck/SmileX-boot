package top.zsmile.mybatis.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import top.zsmile.mybatis.holder.TenantHolder;

@Slf4j
@Component
@Aspect
public class TenantAspect {

    @Pointcut("@annotation(top.zsmile.mybatis.annotation.TenantIgnore)")
    public void tenantAspectPointCut() {

    }

    @Around("tenantAspectPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //执行方法
        try {
            TenantHolder.setIgnore(Boolean.FALSE);
            Object result = joinPoint.proceed();
            return result;
        } finally {
            TenantHolder.clearIgnore();
        }
    }


}
