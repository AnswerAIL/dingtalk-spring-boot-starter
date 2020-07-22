/*
 * Copyright(c) 2015-2019, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.config;

import com.jaemon.dingtalk.support.DefaultNotice;
import com.jaemon.dingtalk.support.Notice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 默认通知配置
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Configuration
@ConditionalOnMissingBean(Notice.class)
public class NoticeConfig {

    @Bean
    public Notice notice() {
        return new DefaultNotice();
    }

}