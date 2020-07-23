/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.support;

import com.jaemon.dingtalk.entity.DingTalkProperties;
import com.jaemon.dingtalk.entity.Message;
import com.jaemon.dingtalk.exception.DingTalkException;

/**
 * 异常回调接口
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public interface Notice {

    /**
     * 通知回调执行
     *
     * @param dingTalkProperties
     *                  dingTalk配置信息
     * @param keyword
     *                  检索关键字(方便日志查询)
     * @param message
     *                  通知信息
     * @param e
     *                  异常对象
     */
    void callback(DingTalkProperties dingTalkProperties, String keyword, Message message, DingTalkException e);

}