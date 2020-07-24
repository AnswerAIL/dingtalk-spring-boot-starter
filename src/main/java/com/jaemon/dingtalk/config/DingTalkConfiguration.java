/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.config;

import com.jaemon.dingtalk.DingTalkRobot;
import com.jaemon.dingtalk.entity.DingTalkProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(DingTalkRobot.class)
@EnableConfigurationProperties(DingTalkProperties.class)
public class DingTalkConfiguration {

    @Autowired
    private DingTalkProperties dingTalkProperties;

    @Bean
    public DingTalkRobot dingTalkRobot(){
        return new DingTalkRobot(dingTalkProperties);
    }

}