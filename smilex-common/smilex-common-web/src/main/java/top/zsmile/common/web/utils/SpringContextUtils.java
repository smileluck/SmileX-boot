package top.zsmile.common.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据class类型，获取Bean
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clz) {
        try {
            return applicationContext.getBean(clz);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 根据名称获取Bean
     *
     * @param name bean 名称
     * @return
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 根据名称，获取bean，并转换为指定类型
     *
     * @param name bean名称
     * @param clz  转换类型
     * @return
     */
    public static <T> T getBean(String name, Class<T> clz) {
        return applicationContext.getBean(name, clz);
    }


    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取HttpServletResponse
     */
    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取Request Origin
     *
     * @return
     */
    public static String getHttpServletRequestOrigin() {
        return getHttpServletRequest().getHeader("Origin");
    }

    /**
     * 停止Springboot
     */
    public static void close() {
        int exit = SpringApplication.exit(getApplicationContext(), () -> 0);
        System.exit(exit);
    }
}
