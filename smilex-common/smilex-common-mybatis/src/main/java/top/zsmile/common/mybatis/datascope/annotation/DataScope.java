package top.zsmile.common.mybatis.datascope.annotation;

import top.zsmile.common.mybatis.datascope.handle.AbstractDataScopeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/02/24/14:54
 * @ClassName: DataScopeAnnotation
 * @Description: DataScopeAnnotation
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {

    /**
     * 数据权限过滤的字段
     */
    String fieldName() default "";

    /**
     * 是否需要过滤
     *
     * @return
     */
    boolean needFilter() default true;

    /**
     * 需要权限过滤的表
     *
     * @return
     */
    String[] filterTable() default {};

    /**
     * 过滤更新操作
     *
     * @return
     */
    boolean opUpdate() default true;

    /**
     * 过滤查询操作
     *
     * @return
     */
    boolean opQuery() default false;

    /**
     * 处理方式，默认不操作
     *
     * @return
     */
    String handleKey() default AbstractDataScopeHandler.NIL;
}
