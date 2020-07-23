/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.config;

import com.jaemon.dingtalk.support.CustomMessage;
import com.jaemon.dingtalk.support.DefaultMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 默认消息格式配置
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Configuration
@ConditionalOnMissingBean(CustomMessage.class)
public class MessageConfig {

    @Bean
    public CustomMessage customMessage() {
        return new DefaultMessage();
    }

}