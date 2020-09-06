package com.jaemon.dingtalk.dinger;

/**
 * ResultHandle
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 2.0
 */
public interface ResultHandle<T> {
    /**
     * resultHandle
     *
     * @param resultType resultType
     * @param t T
     * @return object
     */
    Object resultHandle(Class<?> resultType, T t);
}