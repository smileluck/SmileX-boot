package top.zsmile.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zsmile.annotation.SysLog;
import top.zsmile.common.constant.CommonConstant;
import top.zsmile.common.enums.ModuleType;
import top.zsmile.common.utils.IPUtils;
import top.zsmile.core.api.CommonAuthApi;
import top.zsmile.core.utils.SpringContextUtils;
import top.zsmile.modules.sys.entity.SysLogEntity;
import top.zsmile.modules.sys.service.SysLogService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

@Component
@Aspect
public class SysLogAspect {
    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private CommonAuthApi commonAuthApi;

    @Pointcut("@annotation(top.zsmile.annotation.SysLog)")
    public void sysLogPointcut() {

    }

    @Around("sysLogPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {

        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            // TODO 异常日志
        }
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        saveSysLog(joinPoint, time, result);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long costTime, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLogEntity = new SysLogEntity();

        SysLog sysLogAnno = method.getAnnotation(SysLog.class);
        if (sysLogAnno != null) {
            String title = sysLogAnno.title();
            ModuleType module = sysLogAnno.module();
            int operateType = sysLogAnno.operateType();
            String value = sysLogAnno.value();
            sysLogEntity.setLogTitle(title);
            sysLogEntity.setLogValue(value);
            sysLogEntity.setLogType(sysLogAnno.logType());
            sysLogEntity.setOperateType(getOperateType(method.getName(), operateType));
            sysLogEntity.setLogModule(module.name());
        }

        //获取request
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        sysLogEntity.setIpAddress(IPUtils.getIpAddrByRequest(request));

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogEntity.setMethod(className + "." + methodName);

        // 请求类型
        sysLogEntity.setRequestType(request.getMethod());

        // 耗时
        sysLogEntity.setCostTime(costTime);

        // TODO 请求参数
        sysLogEntity.setRequestParams("");

        // 操作用户Id
        Long userId = commonAuthApi.queryUserId();
        sysLogEntity.setUserId(userId);

        sysLogService.save(sysLogEntity);
    }

    private int getOperateType(String methodName, int operateType) {
        if (operateType > 0) {
            return operateType;
        }
        if (methodName.startsWith("query") || methodName.startsWith("list")) {
            return CommonConstant.SYS_LOG_OPERATE_QUERY;
        } else if (methodName.startsWith("save")) {
            return CommonConstant.SYS_LOG_OPERATE_SAVE;
        } else if (methodName.startsWith("update")) {
            return CommonConstant.SYS_LOG_OPERATE_UPDATE;
        } else if (methodName.startsWith("remove")) {
            return CommonConstant.SYS_LOG_OPERATE_REMOVE;
        } else if (methodName.startsWith("import")) {
            return CommonConstant.SYS_LOG_OPERATE_IMPORT;
        } else if (methodName.startsWith("export")) {
            return CommonConstant.SYS_LOG_OPERATE_EXPORT;
        }
        return CommonConstant.SYS_LOG_OPERATE_QUERY;
    }

}
