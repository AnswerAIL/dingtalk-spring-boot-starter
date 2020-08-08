/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.entity;


import com.jaemon.dingtalk.entity.enums.ResultCode;
import lombok.Data;

/**
 * DingTalk Result
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
public class DingTalkResult {
    /**
     * 响应码
     */
    private String code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 日志id
     */
    private String logid;
    /**
     * 响应数据
     */
    private String data;

    private DingTalkResult(ResultCode resultCode, String logid) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.logid = logid;
    }

    private DingTalkResult(ResultCode resultCode, String logid, String data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.logid = logid;
        this.data = data;
    }

    public static <T> DingTalkResult success(String logId, String data) {
        return new DingTalkResult(ResultCode.SUCCESS, logId, data);
    }

    public static <T> DingTalkResult success(ResultCode resultCode, String logId, String data) {
        return new DingTalkResult(resultCode, logId, data);
    }

    public static DingTalkResult failed(String logid) {
        return new DingTalkResult(ResultCode.FAILED, logid);
    }

    public static DingTalkResult failed(ResultCode resultCode, String logid) {
        return new DingTalkResult(resultCode, logid);
    }

    @Override
    public String toString() {
        return String.format("[code=%s, message=%s, logid=%s, data=%s]",
                code, message, logid, data);
    }
}