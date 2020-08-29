package com.jaemon.dingtalk.exception;

import com.jaemon.dingtalk.entity.enums.ExceptionEnum;

/**
 * 类型异常
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class MsgTypeException extends DingTalkException {
    public MsgTypeException(String msg) {
        super(msg, ExceptionEnum.MSG_TYPE_CHECK);
    }

    public MsgTypeException(Throwable cause) {
        super(cause, ExceptionEnum.MSG_TYPE_CHECK);
    }
}