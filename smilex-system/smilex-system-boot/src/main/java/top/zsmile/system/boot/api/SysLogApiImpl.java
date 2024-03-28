package top.zsmile.system.boot.api;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.filter.PropertyFilter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import top.zsmile.api.system.common.CommonAuthApi;
import top.zsmile.common.log.api.SysLogApi;
import top.zsmile.common.core.constant.CommonConstant;
import top.zsmile.common.log.enums.ModuleType;
import top.zsmile.common.core.utils.IPUtils;
import top.zsmile.common.log.annotation.SysLog;
import top.zsmile.common.core.utils.SpringContextUtils;
import top.zsmile.modules.sys.entity.SysLogEntity;
import top.zsmile.modules.sys.service.SysLogService;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/05/13/22:57
 * @ClassName: SysLogApiImpl
 * @Description: SysLogApiImpl
 */
@Slf4j
@Component
public class SysLogApiImpl implements SysLogApi {

    @Resource
    private SysLogService sysLogService;

    @Autowired
    private CommonAuthApi commonAuthApi;

    /**
     * 保存正常日志
     *
     * @param joinPoint
     * @param costTime
     * @param result
     */
    @Override
    public void saveLog(ProceedingJoinPoint joinPoint, long costTime, Object result) {
        SysLogEntity sysLogEntity = commonLog(joinPoint, costTime);
        sysLogService.save(sysLogEntity);
    }

    /**
     * 保存错误日志
     *
     * @param joinPoint
     * @param costTime
     * @param errMsg
     */
    @Override
    public void saveErrorLog(ProceedingJoinPoint joinPoint, long costTime, String errMsg) {
        SysLogEntity sysLogEntity = commonLog(joinPoint, costTime, CommonConstant.SYS_LOG_TYPE_ERROR);
        sysLogEntity.setErrMsg(errMsg.length() > 500 ? errMsg.substring(0, 500) : errMsg);
        sysLogService.save(sysLogEntity);
    }


    private SysLogEntity commonLog(ProceedingJoinPoint joinPoint, long costTime) {
        return commonLog(joinPoint, costTime, 0);
    }

    private SysLogEntity commonLog(ProceedingJoinPoint joinPoint, long costTime, int logType) {
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
            sysLogEntity.setLogType(logType > 0 ? logType : sysLogAnno.logType());
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

        // 请求参数
        sysLogEntity.setRequestParams(getParams(joinPoint, request));

        // 操作用户Id
        Long userId = commonAuthApi.queryUserId();
        sysLogEntity.setUserId(userId);

        return sysLogEntity;
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

    /**
     * 获取Request参数
     *
     * @param httpServletRequest
     * @return
     */
    private String getParams(ProceedingJoinPoint joinPoint, HttpServletRequest httpServletRequest) {
        String method = httpServletRequest.getMethod();
        if (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("DELET")) {
            Object[] args = joinPoint.getArgs();
            List<Object> collect = Arrays.stream(args).filter(item -> {
                if (item instanceof ServletRequest || item instanceof ServletResponse || item instanceof InputStreamSource) {
                    return false;
                }
                return true;
            }).collect(Collectors.toList());
            PropertyFilter profilter = new PropertyFilter() {
                @Override
                public boolean apply(Object o, String name, Object value) {
                    if (value != null && value.toString().length() > 200) {
                        return false;
                    }
                    return true;
                }
            };
            return JSON.toJSONString(collect, profilter);
        } else {
//            Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
//            while (parameterNames.hasMoreElements()) {
//                String param = parameterNames.nextElement();
//                String value = httpServletRequest.getParameter(param);
//
//            }
            return JSON.toJSONString(httpServletRequest.getParameterMap());
        }
    }
}
