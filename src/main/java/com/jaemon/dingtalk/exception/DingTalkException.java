/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.exception;

import com.jaemon.dingtalk.entity.enums.Pairs;
import lombok.Getter;

/**
 * 异常类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DingTalkException extends RuntimeException {
    @Getter
    private Pairs pairs;


    public DingTalkException(String msg, Pairs pairs) {
        super(msg);
        this.pairs = pairs;
    }

    public DingTalkException(Throwable cause, Pairs pairs) {
        super(cause);
        this.pairs = pairs;
    }
}
