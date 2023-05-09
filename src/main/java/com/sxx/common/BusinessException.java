package com.sxx.common;

/**
 * @author SxxStar
 * @description 自定义异常
 */

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
