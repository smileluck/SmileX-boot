package top.zsmile.common.core.exception;

/**
 * 服务类异常
 */
public final class SXException extends RuntimeException {



    public SXException(String message) {
        super(message);
    }

    public SXException(Throwable cause) {
        super(cause);
    }

    public SXException(String message, Throwable cause) {
        super(message, cause);
    }
}
