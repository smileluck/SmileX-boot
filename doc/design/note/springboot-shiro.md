[toc]

---

# 集成

## springboot集成shiro实现权限控制

> 源码地址：[SmileX-boot](https://github.com/smileluck/SmileX-boot)

### Maven版本

引入springboot-start

```xml
<!-- springboot-web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>2.3.5.RELEASE</version>
</dependency>
<!-- shiro-web -->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring-boot-web-starter</artifactId>
    <shiro.version>1.9.0</shiro.version>
</dependency>
```

### AuthenticationToken

```java
/**
 * TOKEN
 */
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
```

### Filter

```java

/**
 * 过滤器
 */
public class OAuth2Filter extends BasicHttpAuthenticationFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if (StringUtils.isBlank(token)) {
            return null;
        }

        return new OAuth2Token(token);
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return executeLogin(request, response);
        } catch (Exception e) {
            JwtUtils.responseError(response, ResultCode.NO_AUTH, CommonConstant.S_INVALID_TOKEN);
            return false;
        }
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        //处理登录失败的异常
        JwtUtils.responseError(response, ResultCode.NO_AUTH, CommonConstant.S_INVALID_TOKEN);
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader(CommonConstant.S_ACCESS_TOKEN);

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(CommonConstant.S_ACCESS_TOKEN);
        }

        return token;
    }


}

```

### Realm

```java

public class OAuth2Realm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        
        // 授权逻辑
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        if (token == null) {
            log.info("————————身份认证失败————————，IP地址记录：" + IPUtils.getIpAddrByRequest(SpringContextUtils.getHttpServletRequest()));
            throw new AuthenticationException("身份验证失败");
        }

        // token验证逻辑


        return new SimpleAuthenticationInfo(userId, token, getName());
    }
}
```

### Shiro配置

```java

@Configuration
public class ShiroConfig {

    /**
     * 注入realm
     * @return
     */
    @Bean("oAuth2Realm")
    public OAuth2Realm oAuth2Realm() {
        OAuth2Realm realm = new OAuth2Realm();
        return realm;
    }

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager,ShiroFilterChainDefinition shiroFilterChainDefinition) {
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

        securityManager.setRealms(realms);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    @Bean
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

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

```



### 通用的工具

#### JwtUtils.responseError

```java
public static void responseError(ServletResponse response, Integer code, String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        R fail = R.fail(code, msg);
        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            httpServletResponse.setContentType("application/json;charset=utf-8");
httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            outputStream.print(JSON.toJSONString(fail));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```



## shiro的几种CacheManager

看官方的文档，提供了三种CacheManager。

1. MemoryConstrainedCacheManager（仅适用于单体应用）
2. HazelcastCacheManager
3. EhCacheManager

第三方提供的：

1. https://github.com/alexxiyang/shiro-redis。基于jedis，[文档](http://alexxiyang.github.io/shiro-redis/)


对于Hazelcast没有使用过，EhCache现在我使用的比较少，所以我这里

### MemoryConstrainedCacheManager
#### 使用

直接注入配置即可。

```java
@Bean
protected CacheManager cacheManager() {
    return new MemoryConstrainedCacheManager();
}
```
#### 源码查看

这里简单查看一下MemoryConstrainedCacheManager的源码。

```java
// 继承了一个AbstractCacheManager
public class MemoryConstrainedCacheManager extends AbstractCacheManager {
    public MemoryConstrainedCacheManager() {
    }

    protected Cache createCache(String name) {
        return new MapCache(name, new SoftHashMap());
    }
}

public abstract class AbstractCacheManager implements CacheManager, Destroyable {
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap();

    public AbstractCacheManager() {
    }

    public <K, V> Cache<K, V> getCache(String name) throws IllegalArgumentException, CacheException {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Cache name cannot be null or empty.");
        } else {
            Cache cache = (Cache)this.caches.get(name);
            if (cache == null) {
                cache = this.createCache(name);
                Cache existing = (Cache)this.caches.putIfAbsent(name, cache);
                if (existing != null) {
                    cache = existing;
                }
            }

            return cache;
        }
    }
}

public interface CacheManager {
    <K, V> Cache<K, V> getCache(String var1) throws CacheException;
}

public interface Destroyable {
    void destroy() throws Exception;
}

```

可以看见AbstractCacheManager实现了CacheManager和Destoryable接口，实现了获取缓存和销毁的操作。

同事在AbstractCacheManager的代码内，我们可以看到它是使用了ConcurrentHashMap作为并发存储容器的。

### shiro-redis
#### 使用

##### 导入Maven依赖

```xml
 <dependency>
     <groupId>org.crazycake</groupId>
     <artifactId>shiro-redis</artifactId>
     <version>3.3.1</version>
     <exclusions>
         <!-- 排除shiro-core包，可选 -->
         <exclusion>
             <groupId>org.apache.shiro</groupId>
             <artifactId>shiro-core</artifactId>
         </exclusion>
     </exclusions>
</dependency>
```



# 问题记录

## Springboot+Shiro，使用postman会显示404，需要返回未授权响应(JSON格式)

### 问题描述

今天当我配置完springboot+shiro后，我用postman返回任何接口都会返回404。

结果显示：

![](springboot-shiro.assets/1655523548495.png)

配置信息：


```java

    @Bean(name = "filterShiroFilterRegistrationBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager,ShiroFilterChainDefinition shiroFilterChainDefinition) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        				shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());
        return shiroFilterFactoryBean;
    }
@Bean
public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
    chainDefinition.addPathDefinition("/sys/login", "anon");
    chainDefinition.addPathDefinition("/**", "authc");
    return chainDefinition;
}
```

很奇怪没有明白为什么会显示404，然后就去查找了官方的问题，发现在使用 authc 拦截时，如果没有通过验证，那么会重定向到 LoginUrl，可是我这里只想返回401无权限访问。

这里插一句题外话，如果把 shiroFilterFactoryBean 的beanName换成 shiroFilterFactoryBean，如下代码所示：

```java
@Bean(name = "shiroFilterFactoryBean")
public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager,ShiroFilterChainDefinition shiroFilterChainDefinition) {

    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());
    return shiroFilterFactoryBean;
}
```

那么这里的异常界面就会显示为：提示重定向次数过多。

![1655523801051](springboot-shiro.assets/1655523801051.png)

###  定位原因

那么如何解决这个重定向 LoginUrl 问题呢？其实我们可以在官方文档[默认过滤器](https://shiro.apache.org/web.html#default_filters)找到答案。

我们看一下过滤器 authc 对应的过滤器  [org.apache.shiro.web.filter.authc.FormAuthenticationFilter](https://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authc/FormAuthenticationFilter.html) 的源码。

```java
// 继承了 AuthenticatingFilter 认证过滤器
public class FormAuthenticationFilter extends AuthenticatingFilter {
    
    // 无权限
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }

                return this.executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }

                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
            }

            this.saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }
    
    //保存请求并重定向到登录页面
     protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        redirectToLogin(request, response);
    }
    
    // 跳转登录页面
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        String loginUrl = getLoginUrl();
        WebUtils.issueRedirect(request, response, loginUrl);
    }

}
```

这里提取了部分源码，可以看出当用户无权限访问路径时，authc过滤器会将我们的请求重定向到 loginUrl，我们可以在shiroFilterFactoryBean里面配置它。这样就可以跳转到对应的路径了。

```java
shiroFilterFactoryBean.setLoginUrl("/login");
```

既然我们知道了使用 authc 过滤器时，无权限会发生跳转的原因，那么我们应该如何实现返回JSON呢？

### 解决方案

我们可以自定义拦截器继承 AuthenticatingFilter 类。

```java
public class ShiroFilter extends AuthenticatingFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = getRequestToken(httpServletRequest);
        if (StringUtils.isBlank(token)) {
            String json = "{\"code\":401,\"msg\":\"非法token\",\"success\":false}";
            httpServletResponse.getWriter().print(json);
            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader(CommonConstant.S_ACCESS_TOKEN);

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(CommonConstant.S_ACCESS_TOKEN);
        }

        return token;
    }


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if (StringUtils.isBlank(token)) {
            return null;
        }

        return new OAuth2Token(token);
    }
}
```

 通过重新重写 onAccessDenied 方法，我们就能实现当验证失败时返回json的功能，告知用户无权限操作。这里还需要把过滤器加载到 ShiroFilterFactoryBean 中。

```java

@Bean(name = "shiroFilterFactoryBean")
public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager,ShiroFilterChainDefinition shiroFilterChainDefinition) {

    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

    // 这里将过滤器类添加进去，名称为 myAuthc 
    Map<String, Filter> filters = new HashMap<>();
    filters.put("myAuthc", new ShiroFilter());
    shiroFilterFactoryBean.setFilters(filters);
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

    return shiroFilterFactoryBean;
}

@Bean
public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
    chainDefinition.addPathDefinition("/sys/login", "anon");
    // 将所有的都使用 myAuthc拦截
    chainDefinition.addPathDefinition("/**", "myAuthc");
    return chainDefinition;
}

```

让我们看一下效果。

![1655544256383](springboot-shiro.assets/1655544256383.png)

### 总结

其实当我们使用authc过滤器，出现404的现象是正常的，因为授权失败了后会自动跳转登陆页面，那我们没有配置这个登陆页面时，就会出现404。所以我们可以通过自定义过滤器并使用的方式，从而返回JSON或者其它自己所需要的授权失败结果。