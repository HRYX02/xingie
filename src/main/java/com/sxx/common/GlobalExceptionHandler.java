package com.sxx.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author TEIC-Skills
 * @description 全局异常异常
 */

@ControllerAdvice(annotations = {RestController.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @author TEIC-Skills
     * @description 处理SQL表数据唯一异常
     * @exception SQLIntegrityConstraintViolationException
     */
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

    /**
     * @author TEIC-Skills
     * @description 处理自定义异常，一旦有异常便向前端返回bx.getMessage()
     * @exception BusinessException
     */
    @ExceptionHandler(BusinessException.class)
    public R<String> exceptionHandler(BusinessException bx){
        return R.error(bx.getMessage());
    }
}
