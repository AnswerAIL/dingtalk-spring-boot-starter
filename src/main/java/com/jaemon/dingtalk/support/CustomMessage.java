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

/**
 * 自定义消息接口
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public interface CustomMessage {

    /**
     * 自定义消息
     *
     * @param dingTalkProperties dingtalk参数
     * @param keyword 关键字(方便日志检索)
     * @param content 内容
     *
     * @return 消息内容字符串
     */
    String message(DingTalkProperties dingTalkProperties, String keyword, String content);

}