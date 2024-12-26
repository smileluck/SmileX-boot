[toc]

---

# 说明

1. 使用了 spring-boot-starter-data-redis 和 redisson 集成。
2. 目前仅支持单机应用，但是 redisson 的配置比较和 data-redis 的集成不太适配。
3. 建议使用 smilex-common-redisson。

# 配置

```yaml
# redis
spring:
  redis:
    database: 0
    host: 127.0.0.1
    password:
    port: 6379
    timeout: 5000
    lettuce:
      pool:
        max-active: 50
        min-idle: 5
        max-idle: 8
        max-wait: 3000ms
        time-between-eviction-runs: 1
```