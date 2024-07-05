package top.zsmile.common.core.exception;

/**
 * 工具类异常
 */
public final class SXUtilException extends RuntimeException {

    public SXUtilException(String message) {
        super(message);
    }

    public SXUtilException(Throwable cause) {
        super(cause);
    }

    public SXUtilException(String message, Throwable cause) {
        super(message, cause);
    }
}
