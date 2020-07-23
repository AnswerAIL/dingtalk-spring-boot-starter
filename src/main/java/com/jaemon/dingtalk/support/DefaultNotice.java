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
import lombok.extern.slf4j.Slf4j;

/**
 * 默认消息通知
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Slf4j
public class DefaultNotice implements Notice {
    @Override
    public void callback(DingTalkProperties dingTalkProperties, String keyword, String message, DingTalkException e) {
      log.info("异常静默处理");
    }
}