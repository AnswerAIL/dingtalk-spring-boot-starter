/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 签名返回体
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class SignResult extends SignBase {
    /**
     * 秘钥
     */
    private String sign;
    /**
     * 时间戳
     */
    private Long timestamp;

    @Override
    public String transfer() {
        StringBuilder signStr = new StringBuilder(SEPERATOR);
        signStr
                .append("sign=").append(this.sign)
                .append(SEPERATOR)
                .append("timestamp=").append(this.timestamp);
        return signStr.toString();
    }
}