package top.zsmile.common.log.api;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/05/13/22:34
 * @ClassName: SysLogApi
 * @Description: SysLogApi
 */
public interface SysLogApi {
    /**
     * 保存日志
     *
     * @param joinPoint
     * @param costTime
     * @param result
     */
    void saveLog(ProceedingJoinPoint joinPoint, long costTime, Object result);

    /**
     * 保存异常日志
     *
     * @param joinPoint
     * @param costTime
     * @param errMsg
     */
    void saveErrorLog(ProceedingJoinPoint joinPoint, long costTime, String errMsg);
}
