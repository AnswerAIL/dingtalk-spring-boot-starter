/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.config;

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
}