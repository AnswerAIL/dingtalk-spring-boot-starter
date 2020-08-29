package com.jaemon.dingtalk.exception;

import com.jaemon.dingtalk.entity.enums.ExceptionEnum;

/**
 * Invalid Properties Format Exception
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class InvalidPropertiesFormatException extends DingTalkException {
    public InvalidPropertiesFormatException(String msg) {
        super(msg, ExceptionEnum.PROPERTIES_ERROR);
    }

    public InvalidPropertiesFormatException(Throwable cause) {
        super(cause, ExceptionEnum.PROPERTIES_ERROR);
    }
}