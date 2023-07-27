package top.zsmile.system.boot.modules.demo.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * Sse服务
 */
public interface SseService {

    /**
     * 连接
     *
     * @param clientId
     * @return
     */
    SseEmitter connect(String clientId);

    /**
     * 发送
     *
     * @param clientId
     * @param content
     * @return
     */
    boolean send(String clientId, String content);

    /**
     * 关闭
     *
     * @param clientId
     * @return
     */
    boolean close(String clientId);

}
