[toc]

--- 

# 说明
> https://github.com/redisson/redisson/wiki/2.-Configuration#262-single-instance-yaml-config-format
> https://github.com/redisson/redisson/tree/master/redisson-spring-boot-starter
1. 在resource 目录下 添加 redisson.yaml 参考配置


# 配置

## 单机配置

```yaml
---
singleServerConfig:
  idleConnectionTimeout: 10000
  connectTimeout: 10000
  timeout: 3000
  retryAttempts: 3
  retryInterval: 1500
  password: null
  subscriptionsPerConnection: 5
  clientName: null
  address: "redis://127.0.0.1:6379"
  subscriptionConnectionMinimumIdleSize: 1
  subscriptionConnectionPoolSize: 50
  connectionMinimumIdleSize: 24
  connectionPoolSize: 64
  database: 0
  dnsMonitoringInterval: 5000
threads: 16
nettyThreads: 32
codec: !<org.redisson.codec.Kryo5Codec> {}
transportMode: "NIO"
```