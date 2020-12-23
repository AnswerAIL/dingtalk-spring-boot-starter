/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dingerframework.config;

import com.dingerframework.sign.DingTalkSignAlgorithm;
import com.dingerframework.sign.DingerSignAlgorithm;
import com.dingerframework.support.*;
import com.dingerframework.multi.MultiDingerAlgorithmInjectRegister;
import com.dingerframework.support.client.DingerHttpClient;
import com.dingerframework.support.client.DingerHttpTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.dingerframework.constant.DingerConstant.MARKDOWN_MESSAGE;
import static com.dingerframework.constant.DingerConstant.TEXT_MESSAGE;

/**
 *  实例化bean配置
 *
 * @author Jaemon
 * @since 1.0
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public DingerHttpClient dingerHttpClient() {
        return new DingerHttpTemplate();
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
    @ConditionalOnMissingBean(DingerSignAlgorithm.class)
    public DingerSignAlgorithm dingerSignAlgorithm() {
        return new DingTalkSignAlgorithm();
    }


    /**
     * 默认dkid生成配置
     *
     * @return 返回dkid
     */
    @Bean
    @ConditionalOnMissingBean(DingerIdGenerator.class)
    public DingerIdGenerator dingerIdGenerator() {
        return new DefaultDingerIdGenerator();
    }

    /**
     * 默认异步执行回调实例
     *
     * @return 回调实例
     */
    @Bean
    @ConditionalOnMissingBean(DingerAsyncCallback.class)
    public DingerAsyncCallback dingerAsyncCallback() {
        return new DefaultDkCallable();
    }

    @Bean
    @ConditionalOnMissingBean(DingerExceptionCallback.class)
    public DingerExceptionCallback dingerExceptionCallback() {
        return new DefaultDingerExceptionCallback();
    }

    /**
     * 自定义监控事件
     *
     * @return notification instance
     */
    @Bean
    @ConditionalOnMissingBean(MonitorEventNotification.class)
    public MonitorEventNotification monitorEventNotification() {
        return new DefaultApplicationEventNotification();
    }

    @Bean
    public static MultiDingerAlgorithmInjectRegister multiDingerAlgorithmInjectRegister() {
        return new MultiDingerAlgorithmInjectRegister();
    }
}