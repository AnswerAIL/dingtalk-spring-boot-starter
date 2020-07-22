/*
 * Copyright(c) 2015-2019, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DingTalkException extends RuntimeException {

    public DingTalkException(String msg) {
        super(msg);
    }

    public DingTalkException(Throwable cause) {
        super(cause);
    }
}
