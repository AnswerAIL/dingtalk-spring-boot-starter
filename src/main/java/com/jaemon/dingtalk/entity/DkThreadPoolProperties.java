/*
 * Copyright(c) 2015-2020, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.jaemon.dingtalk.constant.DkConstant.DEFAULT_THREAD_NAME_PREFIX;

/**
 * DINGTALK线程池参数配置-用于异步处理
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
@Data
@ConfigurationProperties(prefix = "spring.dingtalk.executor-pool")
public class DkThreadPoolProperties {
    /**
     * 线程池维护线程的最小数量
     */
    private int coreSize = 10;
    /**
     * 线程池维护线程的最大数量
     */
    private int maxSize = 50;
    /**
     * 空闲线程的存活时间
     */
    private int keepAliveSeconds = 60;
    /**
     * 持有等待执行的任务队列
     */
    private int queueCapacity = 2;
    /**
     * 线程名称前缀
     */
    private String threadNamePrefix = DEFAULT_THREAD_NAME_PREFIX;
}