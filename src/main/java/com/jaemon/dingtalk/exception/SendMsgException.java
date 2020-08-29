package com.jaemon.dingtalk.exception;

import com.jaemon.dingtalk.entity.enums.ExceptionEnum;

/**
 * 发送消息异常
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class SendMsgException extends DingTalkException {

    public SendMsgException(String msg) {
        super(msg, ExceptionEnum.SEND_MSG);
    }

    public SendMsgException(Throwable cause) {
        super(cause, ExceptionEnum.SEND_MSG);
    }
}