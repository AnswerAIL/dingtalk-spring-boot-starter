package com.jaemon.dingtalk.exception;

import com.jaemon.dingtalk.entity.enums.ExceptionEnum;

/**
 * 配置异常
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class ConfigurationException extends DingTalkException {
    public ConfigurationException(String msg) {
        super(msg, ExceptionEnum.CONFIG_ERROR);
    }

    public ConfigurationException(Throwable cause) {
        super(cause, ExceptionEnum.CONFIG_ERROR);
    }
}