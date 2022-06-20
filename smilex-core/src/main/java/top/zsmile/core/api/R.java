package top.zsmile.core.api;

import org.springframework.util.ObjectUtils;

import javax.annotation.Nullable;
import java.io.Serializable;

public class R<T> implements Serializable {
    public static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;
    private boolean success;

    private R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = ResultCode.SUCCESS.getCode() == code;
    }

    private R(int code, String msg) {
        this(code, msg, null);
    }

    private R(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessage());
    }

    private R(ResultCode resultCode, String msg) {
        this(resultCode.getCode(), msg);
    }

    private R(ResultCode resultCode, T data) {
        this(resultCode.getCode(), resultCode.getMessage(), data);
    }

    private R(ResultCode resultCode, String msg, T data) {
        this(resultCode.getCode(), msg, data);
    }


    public static <T> R<T> success() {
        return new R(ResultCode.SUCCESS);
    }

    public static <T> R<T> success(ResultCode resultCode) {
        return new R(resultCode);
    }

    public static <T> R<T> success(ResultCode resultCode, String msg) {
        return new R(resultCode, msg);
    }

    public static <T> R<T> success(String msg) {
        return new R(ResultCode.SUCCESS, msg);
    }

    public static <T> R<T> success(T data) {
        return new R(ResultCode.SUCCESS, data);
    }

    public static <T> R<T> success(String msg, T data) {
        return new R(ResultCode.SUCCESS, msg, data);
    }

    public static <T> R<T> fail() {
        return new R(ResultCode.FAILURE);
    }

    public static <T> R<T> fail(Integer code, String msg) {
        return new R(code, msg);
    }

    public static <T> R<T> fail(ResultCode resultCode) {
        return new R(resultCode);
    }

    public static <T> R<T> fail(ResultCode resultCode, String msg) {
        return new R(resultCode, msg);
    }

    public static <T> R<T> fail(String msg) {
        return new R(ResultCode.FAILURE, msg);
    }

    public static <T> R<T> fail(T data) {
        return new R(ResultCode.FAILURE, data);
    }

    public static <T> R<T> fail(String msg, T data) {
        return new R(ResultCode.FAILURE, msg, data);
    }

    public static boolean isSuccess(@Nullable R r) {
        return ObjectUtils.nullSafeEquals(ResultCode.SUCCESS, r.getCode());
    }

    public static boolean isNotSuccess(@Nullable R r) {
        return !isSuccess(r);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
