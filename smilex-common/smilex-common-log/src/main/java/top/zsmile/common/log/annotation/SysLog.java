package top.zsmile.common.log.annotation;

import top.zsmile.common.core.constant.CommonConstant;
import top.zsmile.common.log.enums.ModuleType;
import java.lang.annotation.*;

/**
 * 系统日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 所属模块
     *
     * @return ModuleType
     */
    ModuleType module() default ModuleType.SYS;

    /**
     * 标题
     * 例如：菜单管理
     *
     * @return
     */
    String title() default "";

    /**
     * 日志类型
     *
     * @return 1:登录日志;2:操作日志;3:定时任务;4:异常日志;
     */
    int logType() default CommonConstant.SYS_LOG_TYPE_OPERATE;

    /**
     * 操作类型
     *
     * @return （1查询，2添加，3修改，4删除，5导入，6导出）
     */
    int operateType() default CommonConstant.SYS_LOG_OPERATE_QUERY;

    /**
     * 日志内容
     *
     * @return
     */
    String value() default "";
}
