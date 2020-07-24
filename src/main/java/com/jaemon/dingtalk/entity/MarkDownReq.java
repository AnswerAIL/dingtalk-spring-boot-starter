/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.entity;

import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Markdown 消息格式实体
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
@Builder
public class MarkDownReq extends Message {

    private MarkDown markdown;

    public MarkDownReq(MarkDown markdown) {
        setMsgtype(MsgTypeEnum.MARKDOWN.type());
        this.markdown = markdown;
    }

    @Data
    @AllArgsConstructor
    public static class MarkDown {
        /**
         * 首屏会话透出的展示内容
         */
        private String title;
        /**
         * markdown格式的消息
         */
        private String text;
    }
}