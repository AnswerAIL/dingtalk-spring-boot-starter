/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.config;

import com.jaemon.dingtalk.entity.DkThreadPoolProperties;
import com.jaemon.dingtalk.support.AsyncCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import static com.jaemon.dingtalk.constant.DkConstant.DINGTALK_EXECUTOR;

/**
 * DINGTALK线程池配置类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Configuration
@Conditional(AsyncCondition.class)
@ConditionalOnMissingBean(name = DINGTALK_EXECUTOR)
@EnableConfigurationProperties({DkThreadPoolProperties.class})
public class DkThreadPoolConfig {


    @Bean(name = DINGTALK_EXECUTOR)
    public Executor dingTalkExecutor(DkThreadPoolProperties threadPoolProperties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(threadPoolProperties.getCoreSize());
        // 最大线程数
        executor.setMaxPoolSize(threadPoolProperties.getMaxSize());
        // 线程最大空闲时间
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        // 队列大小
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        // 指定用于新创建的线程名称的前缀
        executor.setThreadNamePrefix(threadPoolProperties.getThreadNamePrefix());

        // 使用自定义拒绝策略, 直接抛出异常
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 等待任务完成时再关闭线程池--表明等待所有线程执行完
        executor.setWaitForTasksToCompleteOnShutdown(true);

        // 初始化
        executor.initialize();
        return executor;
    }

}