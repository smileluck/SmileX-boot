## 使用步骤：

1. 主要配置说明如下：

```
# 微信公众号
wechat:
  mp:
    useRedis: false # 使用redis 
    default:  # 默认appid
    configs:
      - appId: 
        appSecret: 
        token: 
        aesKey: 
```
2. 配置微信公众号中的接口地址：
   ```java
         
   @RestController
   @RequestMapping("/wx/mp")
   public class WxMpController {
   
       private final static Logger logger = LoggerFactory.getLogger(WxMpController.class);
   
       @Resource
       private IWechatMpService wechatMpService;
   
       @GetMapping(produces = "text/plain;charset=utf-8")
       public String index(WechatConfigParams params) {
           if (wechatMpService.checkSignature(params)) {
               return params.getEchostr();
           }
           return "未知异常";
       }
       
       @PostMapping(produces = "application/xml; charset=UTF-8")
       public String post(@RequestBody String requestBody,
                          WechatNotifyParams params) {
           logger.info("\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                           + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                   params.getOpenid(), params.getSignature(), params.getEncrypt_type(), params.getMsg_signature(), params.getTimestamp(), params.getNonce(), requestBody);
   
           if (!wechatMpService.checkSignature(params)) {
               throw new IllegalArgumentException("非法请求！");
           }
   
           if (!wechatMpService.repeatNotify(params)) {
               return Strings.EMPTY;
           }
   
           return wechatMpService.handleMessage(params, requestBody);
       }
   }
   ```
3. 根据自己需要修改各个handler的实现，加入自己的业务逻辑。
	