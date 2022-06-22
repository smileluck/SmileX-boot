package top.zsmile.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisClusterManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.util.StringUtils;
import top.zsmile.auth.filter.OAuth2Filter;
import top.zsmile.auth.realm.OAuth2Realm;
import top.zsmile.common.utils.StringPool;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.*;

@Slf4j
@Configuration
public class ShiroConfig {

    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 注入realm
     *
     * @return
     */
    @Bean("oAuth2Realm")
    public OAuth2Realm oAuth2Realm() {
        OAuth2Realm realm = new OAuth2Realm();
        return realm;
    }

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager, ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new OAuth2Filter());
        shiroFilterFactoryBean.setFilters(filters);

        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());


        return shiroFilterFactoryBean;
    }

    /**
     * 配置拦截
     *
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/sys/login", "anon");
        chainDefinition.addPathDefinition("/**", "oauth2");
        return chainDefinition;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 注入realm 到securityManager
        List<Realm> realms = new ArrayList<>();
        realms.add(oAuth2Realm());

        /**
         * 禁用session管理
         * 根据需要判断，开启后，所有提交都需要验证Token，
         * 否则Login成功后，根据cookie获取session。
         */
//        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
//        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
//        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//        securityManager.setSubjectDAO(subjectDAO);

        securityManager.setRealms(realms);
        securityManager.setCacheManager(cacheManager());
        securityManager.setSessionManager(defaultSessionManager());
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    @Bean
    public DefaultSessionManager defaultSessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        //有效期30分钟
        defaultWebSessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        defaultWebSessionManager.setCacheManager(cacheManager());
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        return defaultWebSessionManager;
    }

    /***
     * 单机使用
     * @return
     */
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

//    /**
//     * cacheManager 缓存 redis实现
//     * 使用的是shiro-redis开源插件
//     *
//     * @return
//     */
//    public RedisCacheManager cacheManager() {
//        log.info("====初始化RedisCache====");
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
//        redisCacheManager.setPrincipalIdFieldName("id");
//        //用户权限信息缓存时间
//        redisCacheManager.setExpire(200000);
//        return redisCacheManager;
//    }

//    /**
//     * 配置shiro redisManager
//     * 使用的是shiro-redis开源插件
//     * TODO 后续考虑自己实现一个。完全使用Lettuce，而不使用Jedis的。
//     *
//     * @return
//     */
//    @Bean
//    public IRedisManager redisManager() {
//        log.info("====初始化RedisManager====");
//        IRedisManager manager;
//        // redis 单机支持
//        if (lettuceConnectionFactory.getClusterConfiguration() == null || lettuceConnectionFactory.getClusterConfiguration().getClusterNodes().isEmpty()) {
//            RedisManager redisManager = new RedisManager();
//            redisManager.setHost(lettuceConnectionFactory.getHostName() + StringPool.COLON + lettuceConnectionFactory.getPort());
//            redisManager.setDatabase(lettuceConnectionFactory.getDatabase());
//            redisManager.setTimeout(0);
//            if (!StringUtils.isEmpty(lettuceConnectionFactory.getPassword())) {
//                redisManager.setPassword(lettuceConnectionFactory.getPassword());
//            }
//            manager = redisManager;
//        } else {
//            //TODO redis集群支持，优先使用集群配置
//            manager = null;
//        }
//        return manager;
//    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
