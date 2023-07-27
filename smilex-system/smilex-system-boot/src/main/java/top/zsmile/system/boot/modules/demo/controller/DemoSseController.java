package top.zsmile.system.boot.modules.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import top.zsmile.common.core.api.R;
import top.zsmile.system.boot.modules.demo.service.SseService;

import javax.annotation.Resource;

/**
 * SSE测试
 */
@RestController
@RequestMapping("/demo/sse")
public class DemoSseController {

    @Resource
    private SseService sseService;

    @RequestMapping(value = "/subscribe", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter subscribe(String clientId) {
        return sseService.connect(clientId);
    }


    @RequestMapping(value = "/send")
    public R send(String clientId, String content) {
        if (sseService.send(clientId, content)) {
            return R.success();
        }
        return R.fail();
    }


    @RequestMapping(value = "/close")
    public R close(String clientId) {
        sseService.close(clientId);
        return R.success();
    }


}
