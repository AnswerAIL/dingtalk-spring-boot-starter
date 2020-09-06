package com.jaemon.dingtalk;

import com.jaemon.dingtalk.config.HttpClient;
import com.jaemon.dingtalk.sign.DkSignAlgorithm;
import com.jaemon.dingtalk.support.CustomMessage;
import com.jaemon.dingtalk.support.DkCallable;
import com.jaemon.dingtalk.support.DkIdGenerator;
import com.jaemon.dingtalk.support.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.Executor;

import static com.jaemon.dingtalk.constant.DkConstant.*;

/**
 *  DingTalk Manager Builder
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
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
    @Qualifier(DINGTALK_EXECUTOR)
    Executor dingTalkExecutor;
    @Autowired
    DkCallable dkCallable;

    public DingTalkManagerBuilder() {
    }

    /**
     * custom http client
     *
     * @param httpClient httpClient
     * @return this
     */
    public DingTalkManagerBuilder httpClient(HttpClient httpClient) {
        if (httpClient != null) {
            this.httpClient = httpClient;
        }
        return this;
    }

    /**
     * custom exception callback
     *
     * @param notice notice
     * @return this
     */
    public DingTalkManagerBuilder notice(Notice notice) {
        if (notice != null) {
            this.notice = notice;
        }
        return this;
    }

    /**
     * custom text message format for {@link com.jaemon.dingtalk.entity.enums.MsgTypeEnum#TEXT}
     *
     * @param textMessage textMessage
     * @return this
     */
    public DingTalkManagerBuilder textMessage(CustomMessage textMessage) {
        if (textMessage != null) {
            this.textMessage = textMessage;
        }
        return this;
    }

    /**
     * custom markdown message format for {@link com.jaemon.dingtalk.entity.enums.MsgTypeEnum#MARKDOWN}
     *
     * @param markDownMessage markDownMessage
     * @return this
     */
    public DingTalkManagerBuilder markDownMessage(CustomMessage markDownMessage) {
        if (markDownMessage != null) {
            this.markDownMessage = markDownMessage;
        }
        return this;
    }

    /**
     * custom sign algorithm
     *
     * @param dkSignAlgorithm dkSignAlgorithm
     * @return this
     */
    public DingTalkManagerBuilder dkSignAlgorithm(DkSignAlgorithm dkSignAlgorithm) {
        if (dkSignAlgorithm != null) {
            this.dkSignAlgorithm = dkSignAlgorithm;
        }
        return this;
    }

    /**
     * custom id generator
     *
     * @param dkIdGenerator dkIdGenerator
     * @return this
     */
    public DingTalkManagerBuilder dkIdGenerator(DkIdGenerator dkIdGenerator) {
        if (dkIdGenerator != null) {
            this.dkIdGenerator = dkIdGenerator;
        }
        return this;
    }

    /**
     * custom async executor
     *
     * @param dingTalkExecutor dingTalkExecutor
     * @return this
     */
    public DingTalkManagerBuilder dingTalkExecutor(Executor dingTalkExecutor) {
        if (dingTalkExecutor != null) {
            this.dingTalkExecutor = dingTalkExecutor;
        }
        return this;
    }

    /**
     * custom async callback
     *
     * @param dkCallable dkCallable
     * @return this
     */
    public DingTalkManagerBuilder dkCallable(DkCallable dkCallable) {
        if (dkCallable != null) {
            this.dkCallable = dkCallable;
        }
        return this;
    }
}