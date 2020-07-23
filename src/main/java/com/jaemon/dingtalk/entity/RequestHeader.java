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

import java.util.List;

/**
 * 请求头
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
public class RequestHeader {

    private List<Pairs> data;

    @Data
    @AllArgsConstructor
    public static class Pairs {
        private String key;
        private String value;
    }
}