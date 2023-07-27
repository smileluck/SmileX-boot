package top.zsmile.system.boot.modules.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import top.zsmile.common.core.exception.SXException;
import top.zsmile.system.boot.modules.demo.service.SseService;
import top.zsmile.system.boot.modules.demo.session.SseSession;

import java.io.IOException;

@Slf4j
@Service
public class SseServiceImpl implements SseService {
    @Override
    public SseEmitter connect(String clientId) {
        if (SseSession.exists(clientId)) {
            SseSession.remove(clientId);
        }
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onError((err) -> {
            log.error("type: SseSession Error, msg: {} session Id : {}", err.getMessage(), clientId);
            SseSession.onError(clientId, err);
        });

        sseEmitter.onTimeout(() -> {
            log.info("type: SseSession Timeout, session Id : {}", clientId);
            SseSession.remove(clientId);
        });

        sseEmitter.onCompletion(() -> {
            log.info("type: SseSession Completion, session Id : {}", clientId);
            SseSession.remove(clientId);
        });
        SseSession.add(clientId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public boolean send(String clientId, String content) {
        if (SseSession.exists(clientId)) {
            try {
                SseSession.send(clientId, content);
                return true;
            } catch (IOException exception) {
                log.error("type: SseSession send Erorr:IOException, msg: {} session Id : {}", exception.getMessage(), clientId);
            }
        } else {
            throw new SXException("User Id " + clientId + " not Found");
        }
        return false;
    }

    @Override
    public boolean close(String clientId) {
        log.info("type: SseSession Close, session Id : {}", clientId);
        return SseSession.remove(clientId);
    }
}
