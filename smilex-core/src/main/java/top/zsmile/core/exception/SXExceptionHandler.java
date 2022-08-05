package top.zsmile.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.zsmile.core.api.R;
import top.zsmile.core.api.ResultCode;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class SXExceptionHandler {

    /**
     * 自定义异常
     */
    @ExceptionHandler(SXException.class)
    public R handleException(SXException e) {
        log.error(e.getMessage(), e);
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return R.fail(ResultCode.NO_FIND, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.fail("数据库中已存在该记录");
    }

//    @ExceptionHandler(AuthorizationException.class)
//    public R handleAuthorizationException(AuthorizationException e) {
//        log.error(e.getMessage(), e);
//        return R.fail("没有权限，请联系管理员授权");
//    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        if (e.getClass().getName().equals("org.apache.shiro.authz.UnauthorizedException")) {
            return R.fail("操作失败，用户无权限");
        }
        return R.fail("操作异常，请联系管理员");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        return R.fail("文件大小超出10MB限制, 请压缩或降低文件质量! ");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public R handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        return R.fail(e.getMessage());
    }


    private static final String SPLINT = ",";

    @ExceptionHandler(BindException.class)
    public R BindException(BindException e) {
        log.error(e.getMessage(), e);
        return R.fail(fieldErrorToMessage(e.getBindingResult().getFieldErrors()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R BindException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return R.fail(fieldErrorToMessage(e.getBindingResult().getFieldErrors()));
    }

    private String fieldErrorToMessage(List<FieldError> fieldErrors) {
        StringBuffer sb = new StringBuffer("");
        for (FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getDefaultMessage()).append(SPLINT);
        }
        return sb.substring(0, sb.length() - 1);
    }

}
