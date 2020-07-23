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
import com.jaemon.dingtalk.support.MarkDownMessage;
import com.jaemon.dingtalk.support.TextMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  实例化bean配置
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public HttpClient httpClient() {
        return new HttpClient();
    }


    /**
     * 默认Text消息格式配置
     *
     * @return CustomMessage
     */
    @ConditionalOnMissingBean(name = "textMessage")
    @Bean("textMessage")
    public CustomMessage textMessage() {
        return new TextMessage();
    }

    /**
     * 默认markdown消息格式配置
     *
     * @return CustomMessage
     */
    @ConditionalOnMissingBean(name = "markDownMessage")
    @Bean("markDownMessage")
    public CustomMessage markDownMessage() {
        return new MarkDownMessage();
    }
}