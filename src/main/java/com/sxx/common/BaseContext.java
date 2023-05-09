package com.sxx.common;

/**
 * @author SxxStar
 * @description 通过线程变量赋值用户ID
 */

public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /***
     * @author SxxStar
     * @description 设置线程ID
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /***
     * @author SxxStar
     * @description 获取线程ID
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
