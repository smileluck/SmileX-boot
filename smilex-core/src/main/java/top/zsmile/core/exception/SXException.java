package top.zsmile.core.exception;

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
