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
