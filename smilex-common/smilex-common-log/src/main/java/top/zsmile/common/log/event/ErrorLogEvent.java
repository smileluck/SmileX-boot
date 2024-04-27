package top.zsmile.common.log.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 错误报警
 */
public class ErrorLogEvent extends ApplicationEvent {
    public ErrorLogEvent(Map<String, Object> source) {
        super(source);
    }
}
