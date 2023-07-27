package top.zsmile.system.boot.modules.demo.session;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import top.zsmile.common.core.exception.SXException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SseSession {


    private static Map<String, SseEmitter> sessionMap = new ConcurrentHashMap<>();

    public static void add(String sessionKey, SseEmitter sseEmitter) {
        if (sessionMap.get(sessionKey) != null) {
            throw new SXException("client exists!");
        }
        sessionMap.put(sessionKey, sseEmitter);
    }

    public static boolean exists(String sessionKey) {
        return sessionMap.get(sessionKey) != null;
    }

    public static boolean remove(String sessionKey) {
        SseEmitter sseEmitter = sessionMap.get(sessionKey);
        if (sseEmitter != null) {
            sseEmitter.complete();
            sessionMap.remove(sessionKey);
            return true;
        }
        return false;
    }

    public static void onError(String sessionKey, Throwable throwable) {
        SseEmitter sseEmitter = sessionMap.get(sessionKey);
        if (sseEmitter != null) {
            sseEmitter.completeWithError(throwable);
        }
    }

    public static void send(String sessionKey, String content) throws IOException {
        sessionMap.get(sessionKey).send(content);
    }

}
