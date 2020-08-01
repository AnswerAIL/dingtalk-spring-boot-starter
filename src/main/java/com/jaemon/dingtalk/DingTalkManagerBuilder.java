/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk;

import com.jaemon.dingtalk.config.HttpClient;
import com.jaemon.dingtalk.sign.DkSignAlgorithm;
import com.jaemon.dingtalk.support.CustomMessage;
import com.jaemon.dingtalk.support.DkCallable;
import com.jaemon.dingtalk.support.DkIdGenerator;
import com.jaemon.dingtalk.support.Notice;
import lombok.Builder;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.Executor;

import static com.jaemon.dingtalk.constant.DkConstant.MARKDOWN_MESSAGE;
import static com.jaemon.dingtalk.constant.DkConstant.TEXT_MESSAGE;

/**
 *  DingTalk Manager Builder
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Builder
@ToString
public class DingTalkManagerBuilder {
    @Autowired
    HttpClient httpClient;
    @Autowired
    Notice notice;
    @Autowired
    @Qualifier(TEXT_MESSAGE)
    CustomMessage textMessage;
    @Autowired
    @Qualifier(MARKDOWN_MESSAGE)
    CustomMessage markDownMessage;
    @Autowired
    DkSignAlgorithm dkSignAlgorithm;
    @Autowired
    DkIdGenerator dkIdGenerator;
    @Autowired
    Executor dingTalkExecutor;
    @Autowired
    DkCallable dkCallable;

    /**
     * custom http client
     *
     * @param httpClient httpClient
     * @return this
     */
    public DingTalkManagerBuilder httpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    /**
     * custom exception callback
     *
     * @param notice notice
     * @return this
     */
    public DingTalkManagerBuilder notice(Notice notice) {
        this.notice = notice;
        return this;
    }

    /**
     * custom text message format for {@link com.jaemon.dingtalk.entity.enums.MsgTypeEnum#TEXT}
     *
     * @param textMessage textMessage
     * @return this
     */
    public DingTalkManagerBuilder textMessage(CustomMessage textMessage) {
        this.textMessage = textMessage;
        return this;
    }

    /**
     * custom markdown message format for {@link com.jaemon.dingtalk.entity.enums.MsgTypeEnum#MARKDOWN}
     *
     * @param markDownMessage markDownMessage
     * @return this
     */
    public DingTalkManagerBuilder markDownMessage(CustomMessage markDownMessage) {
        this.markDownMessage = markDownMessage;
        return this;
    }

    /**
     * custom sign algorithm
     *
     * @param dkSignAlgorithm dkSignAlgorithm
     * @return this
     */
    public DingTalkManagerBuilder dkSignAlgorithm(DkSignAlgorithm dkSignAlgorithm) {
        this.dkSignAlgorithm = dkSignAlgorithm;
        return this;
    }

    /**
     * custom id generator
     *
     * @param dkIdGenerator dkIdGenerator
     * @return this
     */
    public DingTalkManagerBuilder dkIdGenerator(DkIdGenerator dkIdGenerator) {
        this.dkIdGenerator = dkIdGenerator;
        return this;
    }

    /**
     * custom async executor
     *
     * @param dingTalkExecutor dingTalkExecutor
     * @return this
     */
    public DingTalkManagerBuilder dingTalkExecutor(Executor dingTalkExecutor) {
        this.dingTalkExecutor = dingTalkExecutor;
        return this;
    }

    /**
     * custom async callback
     *
     * @param dkCallable dkCallable
     * @return this
     */
    public DingTalkManagerBuilder dkCallable(DkCallable dkCallable) {
        this.dkCallable = dkCallable;
        return this;
    }
}