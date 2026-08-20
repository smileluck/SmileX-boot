package top.zsmile.common.mybatis.spi;

/**
 * 租户ID提供者 SPI
 * <p>
 * 由业务侧实现并注册为 Spring Bean，供租户拦截器在 SQL 改写时获取当前租户ID。
 * 返回 null 表示当前上下文无租户信息（如系统任务、未登录），拦截器将跳过租户条件注入。
 *
 * @see top.zsmile.common.mybatis.interceptor.TenantLineInnerInterceptor
 */
public interface TenantIdProvider {

    /**
     * 获取当前租户ID
     *
     * @return 租户ID，无租户上下文时返回 null
     */
    Long getTenantId();
}
