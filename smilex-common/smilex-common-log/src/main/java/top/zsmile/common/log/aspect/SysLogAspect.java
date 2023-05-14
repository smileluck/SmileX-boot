package top.zsmile.common.log.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zsmile.api.system.common.SysLogApi;

@Component
@Aspect
public class SysLogAspect {
    @Autowired
    private SysLogApi sysLogApi;


    @Pointcut("@annotation(top.zsmile.common.log.annotation.SysLog)")
    public void sysLogPointcut() {

    }

    @Around("sysLogPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        long beginTime = System.currentTimeMillis();
        try {
            //执行方法
            Object result = joinPoint.proceed();
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            sysLogApi.saveLog(joinPoint, time, result);
            return result;
        } catch (Throwable throwable) {
            // 异常日志
            long time = System.currentTimeMillis() - beginTime;
            sysLogApi.saveErrorLog(joinPoint, time, throwable.getMessage());
            throw throwable;
        }
    }

}
