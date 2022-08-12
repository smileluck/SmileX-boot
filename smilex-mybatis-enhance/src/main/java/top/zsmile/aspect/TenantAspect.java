package top.zsmile.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import top.zsmile.holder.TenantHolder;

@Slf4j
@Aspect
public class TenantAspect {

    @Pointcut("@annotation(top.zsmile.annotation.TenantIgnore)")
    public void tenantAspectPointCut() {

    }

    @Around("tenantAspectPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //执行方法
        try {
            TenantHolder.setIgnore(Boolean.TRUE);
            Object result = joinPoint.proceed();
            return result;
        } finally {
            TenantHolder.clearIgnore();
        }
    }


}
