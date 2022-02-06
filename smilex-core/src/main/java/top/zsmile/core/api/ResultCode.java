package top.zsmile.core.api;

public enum ResultCode implements IResultCode {

    SUCCESS(200, "操作成功"),
    FAILURE(400, "业务异常"),
    NO_AUTH(401, "无操作权限"),
    NO_FIND(404, "没有找到资源"),
    SERVER(500, "服务异常");


    final int code;
    final String msg;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

    private ResultCode(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }
}
