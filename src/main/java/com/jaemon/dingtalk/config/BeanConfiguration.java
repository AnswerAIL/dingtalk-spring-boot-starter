/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.config;

import com.jaemon.dingtalk.sign.DefaultSignAlgorithm;
import com.jaemon.dingtalk.sign.DkSignAlgorithm;
import com.jaemon.dingtalk.support.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.jaemon.dingtalk.constant.DkConstant.MARKDOWN_MESSAGE;
import static com.jaemon.dingtalk.constant.DkConstant.TEXT_MESSAGE;

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
    @ConditionalOnMissingBean(name = TEXT_MESSAGE)
    @Bean(TEXT_MESSAGE)
    public CustomMessage textMessage() {
        return new TextMessage();
    }

    /**
     * 默认markdown消息格式配置
     *
     * @return CustomMessage
     */
    @ConditionalOnMissingBean(name = MARKDOWN_MESSAGE)
    @Bean(MARKDOWN_MESSAGE)
    public CustomMessage markDownMessage() {
        return new MarkDownMessage();
    }


    /**
     * 默认签名算法
     *
     * @return 签名实体
     */
    @Bean
    @ConditionalOnMissingBean(DkSignAlgorithm.class)
    public DkSignAlgorithm dkSignAlgorithm() {
        return new DefaultSignAlgorithm();
    }


    /**
     * 默认dkid生成配置
     *
     * @return 返回dkid
     */
    @Bean
    @ConditionalOnMissingBean(DkIdGenerator.class)
    public DkIdGenerator dkIdGenerator() {
        return new DefaultDkIdGenerator();
    }

    /**
     * 默认异步执行回调实例
     *
     * @return 回调实例
     */
    @Bean
    @ConditionalOnMissingBean(DkCallable.class)
    public DkCallable dkCallable() {
        return new DefaultDkCallable();
    }

    /**
     * notification(default)
     *
     * @return notification instance
     */
    @Bean
    @ConditionalOnMissingBean(Notification.class)
    public Notification notification() {
        return new DefaultApplicationEventNotification();
    }
}