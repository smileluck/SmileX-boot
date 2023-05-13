package top.zsmile.common.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

/**
 * redis读写异常捕获，暂不处理
 *
 * TODO 后续需要调整在做处理
 */
@Slf4j
public class RedisCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
        log.error(e.getMessage(), e);
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
        log.error(e.getMessage(), e);
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
        log.error(e.getMessage(), e);
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        log.error(e.getMessage(), e);
    }
}
