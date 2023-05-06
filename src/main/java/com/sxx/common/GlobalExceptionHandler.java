package com.sxx.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author TEIC-Skills
 */

@ControllerAdvice(annotations = {RestController.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String message = ex.getMessage();
        log.error(ex.getMessage());
        if (message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            message = split[2].substring(1, split.length-1);
            return R.error(message+"已存在");
        }
        return R.error("未知错误");
    }

    @ExceptionHandler(BusinessException.class)
    public R<String> exceptionHandler(BusinessException bx){
        return R.error(bx.getMessage());
    }
}
