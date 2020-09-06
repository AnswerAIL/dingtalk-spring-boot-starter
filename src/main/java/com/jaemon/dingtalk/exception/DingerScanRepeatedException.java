package com.jaemon.dingtalk.exception;

import com.jaemon.dingtalk.entity.enums.ExceptionEnum;

/**
 * DingerScanRepeatedException
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DingerScanRepeatedException extends DingTalkException {
    public DingerScanRepeatedException() {
        super(ExceptionEnum.DINGERSCAN_REPEATED_EXCEPTION);
    }

    public DingerScanRepeatedException(String msg) {
        super(msg, ExceptionEnum.DINGERSCAN_REPEATED_EXCEPTION);
    }

    public DingerScanRepeatedException(Throwable cause) {
        super(cause, ExceptionEnum.DINGERSCAN_REPEATED_EXCEPTION);
    }
}