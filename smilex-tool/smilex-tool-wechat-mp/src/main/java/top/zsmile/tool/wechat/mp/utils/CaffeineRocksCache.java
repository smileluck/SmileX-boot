package top.zsmile.tool.wechat.mp.utils;

import com.alibaba.fastjson2.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.zsmile.tool.wechat.mp.constant.WechatRedisConstant;

import javax.annotation.PreDestroy;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Caffeine 缓存 + RocksDB 数据库做持久化
 */
@Component
public class CaffeineRocksCache implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger("wx-mp");

    private Cache<String, Object> cache;
    private RocksDB db;

    /**
     * 数据恢复
     */
    private void restoreDataFromRocksDB() {
        try (RocksIterator iterator = db.newIterator()) {
            iterator.seekToFirst();
            while (iterator.isValid()) {
                String key = new String(iterator.key());
                Object parse = JSON.parse(iterator.value());
                cache.put(key, parse);
                iterator.next();
            }
        }
    }

    public void set(String key, Object object) {
        cache.put(key, object);
    }

    public Object get(String key) {
        return cache.getIfPresent(key);
    }

    public void clear(String prefix) {
        @NonNull ConcurrentMap<@NonNull String, @NonNull Object> map = cache.asMap();
        map.keySet().removeIf(key -> key.startsWith(prefix));
    }

    public Stream<Map.Entry<@NonNull String, @NonNull Object>> getByPrefix(String prefix) {
        return cache.asMap().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(prefix));
    }

    @PreDestroy
    public void destroy() throws RocksDBException {
        logger.info("=========保存到RocksDB=========");
        ConcurrentMap<String, Object> map = cache.asMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            db.put(entry.getKey().getBytes(), JSON.toJSONBytes(entry.getValue()));
        }
        db.close();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RocksDB.loadLibrary();
        try {
            Options options = new Options();
            options.setCreateIfMissing(true);

            String currentDir = System.getProperty("user.dir");
            File dbFile = new File(currentDir + "/rocksdb/");
            if (!dbFile.exists()) {
                dbFile.mkdirs();
            }
            db = RocksDB.open(options, dbFile.getAbsolutePath());
        } catch (RocksDBException e) {
            e.printStackTrace();
        }

        cache = Caffeine.newBuilder()
                .expireAfter(new Expiry<String, Object>() {
                    @Override
                    public long expireAfterCreate(String key, Object value, long currentTime) {
                        if (key.startsWith(WechatRedisConstant.MP_REPEAT)) {
                            return TimeUnit.SECONDS.toNanos(3);
                        } else if (key.startsWith(WechatRedisConstant.MP_QRCODE)) {
                            return TimeUnit.SECONDS.toNanos(180);
                        }
                        return Long.MAX_VALUE;
                    }

                    @Override
                    public long expireAfterUpdate(String key, Object value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }

                    @Override
                    public long expireAfterRead(String key, Object value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }
                }).build();

        // 数据恢复
        restoreDataFromRocksDB();
    }
}
