/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk;

import com.jaemon.dingtalk.entity.DingTalkProperties;
import com.jaemon.dingtalk.entity.enums.MsgTypeEnum;
import com.jaemon.dingtalk.exception.MsgTypeException;
import com.jaemon.dingtalk.support.CustomMessage;

/**
 * DingTalk Template
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public abstract class AbstractDingTalkSender implements DingTalkSender {
    DingTalkProperties dingTalkProperties;
    DingTalkManagerBuilder dingTalkManagerBuilder;

    public AbstractDingTalkSender(DingTalkProperties dingTalkProperties, DingTalkManagerBuilder dingTalkManagerBuilder) {
        this.dingTalkProperties = dingTalkProperties;
        this.dingTalkManagerBuilder = dingTalkManagerBuilder;
    }

    /**
     * 消息类型校验
     *
     * @param msgType
     *              消息类型
     * @return
     *              消息生成器
     */
    CustomMessage checkMsgType(MsgTypeEnum msgType) {
        if (msgType != MsgTypeEnum.TEXT && msgType != MsgTypeEnum.MARKDOWN) {
            throw new MsgTypeException("暂不支持" + msgType.type() + "类型");
        }

        return msgType == MsgTypeEnum.TEXT ? dingTalkManagerBuilder.textMessage : dingTalkManagerBuilder.markDownMessage;
    }
}