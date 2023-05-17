package top.zsmile.common.datasource.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zsmile.common.core.api.R;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/05/17/11:24
 * @ClassName: DataSourceException
 * @Description: DataSourceException
 */
@Slf4j
@RestControllerAdvice
public class DataSourceExceptionHandler {


    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.fail("数据库中已存在该记录");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public R handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        return R.fail(e.getMessage());
    }

}
