/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.entity.enums;

/**
 * Result Code
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public enum ResultCode {
    SUCCESS("DK000", "success"),

    DINGTALK_DISABLED("DK101", "DingTalk未启用"),

    MESSAGE_TYPE_UNSUPPORTED("DK201", "无法支持的消息类型"),
    SEND_MESSAGE_FAILED("DK202", "消息发送失败"),
    FAILED("DK999", "failed")

    ;

    private String code;
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}