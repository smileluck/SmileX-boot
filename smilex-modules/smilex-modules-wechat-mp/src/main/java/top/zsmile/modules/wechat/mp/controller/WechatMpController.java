package top.zsmile.modules.wechat.mp.controller;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.zsmile.tool.wechat.mp.bean.WechatConfigParams;
import top.zsmile.tool.wechat.mp.bean.WechatNotifyParams;
import top.zsmile.tool.wechat.mp.service.IWechatMpService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wechat/mp")
public class WechatMpController {

    private final static Logger logger = LoggerFactory.getLogger(WechatMpController.class);

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

        String s = wechatMpService.handleMessage(params, requestBody);
        return s;
    }
}
