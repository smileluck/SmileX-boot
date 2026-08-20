package top.zsmile.common.mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.SqlSessionFactoryBeanCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zsmile.api.system.common.CommonAuthApi;
import top.zsmile.common.core.utils.uuid.SnowFlake;
import top.zsmile.common.mybatis.interceptor.FieldDecryptInterceptor;
import top.zsmile.common.mybatis.interceptor.PaginationInnerInterceptor;
import top.zsmile.common.mybatis.interceptor.TenantLineInnerInterceptor;
import top.zsmile.common.mybatis.interceptor.UpdateInterceptor;
import top.zsmile.common.mybatis.spi.TenantIdProvider;
import top.zsmile.common.mybatis.utils.EntityAutoFill;
import top.zsmile.common.mybatis.utils.FieldEncryptor;

/**
 * MyBatis 增强层统一配置（依赖注入式）
 * <p>
 * - 雪花算法单例（workerId/dataCenterId 复用 smilex.server 配置）
 * - TenantIdProvider SPI 默认实现（委托 CommonAuthApi，无实现时返回 null 跳过租户改写）
 * - 拦截器链通过 {@link SqlSessionFactoryBeanCustomizer} 注入：
 *   该扩展点在 sqlSessionFactory Bean 构建时执行，与配置来源
 *   （yml properties / configLocation XML）无关，且不受"工厂被其他 BeanPostProcessor
 *   提前创建"的影响（ConfigurationCustomizer 与 BeanPostProcessor 方案均有此覆盖盲区）。
 * <p>
 * 插件顺序即责任链顺序（先注册在内层）：
 * TenantLine（租户改写，最内层，分页 count SQL 同样被改写） -&gt; Pagination（分页）
 * -&gt; UpdateInterceptor（审计兜底） -&gt; FieldDecrypt（读侧解密）
 */
@Slf4j
@Configuration
@ConditionalOnClass(SqlSessionFactory.class)
@EnableConfigurationProperties(SmilexMybatisProperties.class)
public class MybatisPluginConfiguration {

    @Bean
    public SnowFlake smilexSnowFlake(@Value("${smilex.server.workerId:0}") long workerId,
                                     @Value("${smilex.server.dataCenterId:0}") long dataCenterId) {
        return new SnowFlake(workerId, dataCenterId);
    }

    @Bean
    @ConditionalOnMissingBean(TenantIdProvider.class)
    public TenantIdProvider smilexTenantIdProvider(ObjectProvider<CommonAuthApi> authApiProvider) {
        return () -> {
            CommonAuthApi api = authApiProvider.getIfAvailable();
            return api == null ? null : api.queryTenantId();
        };
    }

    @Bean
    public SqlSessionFactoryBeanCustomizer smilexMybatisPluginCustomizer(
            ObjectProvider<SmilexMybatisProperties> propertiesProvider,
            ObjectProvider<TenantIdProvider> tenantIdProviderProvider,
            ObjectProvider<SnowFlake> snowFlakeProvider) {
        return factoryBean -> {
            SmilexMybatisProperties properties = propertiesProvider.getIfAvailable(SmilexMybatisProperties::new);
            TenantIdProvider tenantIdProvider = tenantIdProviderProvider.getIfAvailable();
            SnowFlake snowFlake = snowFlakeProvider.getIfAvailable();

            EntityAutoFill.initGenerator(snowFlake);
            if (tenantIdProvider != null) {
                EntityAutoFill.initTenantProvider(tenantIdProvider);
            }
            FieldEncryptor.init(properties.getEncryptKey());

            factoryBean.setPlugins(
                    new TenantLineInnerInterceptor(tenantIdProvider, properties.getTenantIgnoreTables()),
                    new PaginationInnerInterceptor(properties.getMaxPageSize()),
                    new UpdateInterceptor(),
                    new FieldDecryptInterceptor());
            log.info("smilex-mybatis 插件链已注册: tenant -> pagination -> updateAudit -> fieldDecrypt (tenantProvider: {})",
                    tenantIdProvider == null ? "无(跳过租户改写)" : tenantIdProvider.getClass().getName());
        };
    }
}
