## 使用步骤：

1. 主要配置说明如下：

```yaml
# 微信支付
wechat:
  pay:
    v3:
      configs:
        - id: main # 配置ID名称
          appid:
          mchid:
          apiKey:
          apiSerialNum:
          mchSerialNum:
          privateKeyPath:
          notifyUrl:
          notifyRefundUrl: 
```

```yaml
# 支付包支付

# 支付宝
ali:
  pay:
    configs:
      - id: main
        appid:
        privateKey:
        alipayPublicKey:
        serveUrl:
        notifyUrl:
        notifyRefundUrl: 
```