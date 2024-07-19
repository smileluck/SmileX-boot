package top.zsmile.system.boot.modules.demo.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "DEMO-SSE测试")
@RestController
@RequestMapping("/demo/sse")
public class DemoSseController {

    @Resource
    private SseService sseService;

    @Schema(description = "订阅")
    @RequestMapping(value = "/subscribe", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter subscribe(String clientId) {
        return sseService.connect(clientId);
    }


    @Schema(description = "发送")
    @RequestMapping(value = "/send")
    public R send(String clientId, String content) {
        if (sseService.send(clientId, content)) {
            return R.success();
        }
        return R.fail();
    }


    @Schema(description = "关闭")
    @RequestMapping(value = "/close")
    public R close(String clientId) {
        sseService.close(clientId);
        return R.success();
    }


}
