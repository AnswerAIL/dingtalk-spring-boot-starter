package com.jaemon.dingtalk.exception;

import com.jaemon.dingtalk.entity.enums.ExceptionEnum;

/**
 * 异步调用异常
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class AsyncCallException extends DingTalkException {
    public AsyncCallException(String msg) {
        super(msg, ExceptionEnum.ASYNC_CALL);
    }

    public AsyncCallException(Throwable cause) {
        super(cause, ExceptionEnum.ASYNC_CALL);
    }
}