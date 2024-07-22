package top.zsmile.common.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnMissingBean(RedissonConfig.class)
public class RedissonClusterConfig {

    @Value("${spring.redis.cluster.nodes}")
    private List<String> nodes;


    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useClusterServers()
                .setNodeAddresses(nodes);
        return Redisson.create(config);
    }
}
