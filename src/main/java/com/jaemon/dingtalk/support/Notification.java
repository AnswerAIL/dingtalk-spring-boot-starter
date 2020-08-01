/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.support;
import com.jaemon.dingtalk.entity.message.MsgType;
import com.jaemon.dingtalk.entity.message.TextReq;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;

/**
 * Notification
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public abstract class Notification {

    /**
     * success Message
     *
     * @param event {@link ApplicationReadyEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message
     */
    public abstract String successMessage(ApplicationReadyEvent event, String projectId);

    /**
     * notification of success
     *
     * @param event {@link ApplicationReadyEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message obj
     */
    public MsgType success(ApplicationReadyEvent event, String projectId) {
        String message = successMessage(event, projectId);
        return message(message);
    };


    /**
     * failed Message
     *
     * @param event {@link ApplicationFailedEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message
     */
    public abstract String failedMessage(ApplicationFailedEvent event, String projectId);

    /**
     * notification of failed
     *
     * @param event {@link ApplicationFailedEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message obj
     */
    public MsgType failed(ApplicationFailedEvent event, String projectId) {
        String message = failedMessage(event, projectId);
        return message(message);
    }

    /**
     * exit Message
     *
     * @param event {@link ContextClosedEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message
     */
    public abstract String exitMessage(ContextClosedEvent event, String projectId);

    /**
     * notification of exit
     *
     * @param event {@link ContextClosedEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message obj
     */
    public MsgType exit(ContextClosedEvent event, String projectId) {
        String message = exitMessage(event, projectId);
        return message(message);
    }

    /**
     * message(default)
     *
     * @param text
     *          消息内容
     * @return
     *          具体消息对象
     */
    public MsgType message(String text) {
        return new TextReq(new TextReq.Text(text));
    }
}