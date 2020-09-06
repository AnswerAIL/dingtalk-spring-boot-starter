package com.jaemon.dingtalk.exception;

import com.jaemon.dingtalk.entity.enums.ExceptionEnum;

/**
 * DingerConfigRepeatedException
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DingerConfigRepeatedException extends DingTalkException {
    public DingerConfigRepeatedException() {
        super(ExceptionEnum.DINGER_CONFIG_REPEATED_EXCEPTION);
    }

    public DingerConfigRepeatedException(String msg) {
        super(msg, ExceptionEnum.DINGER_CONFIG_REPEATED_EXCEPTION);
    }

    public DingerConfigRepeatedException(Throwable cause) {
        super(cause, ExceptionEnum.DINGER_CONFIG_REPEATED_EXCEPTION);
    }
}