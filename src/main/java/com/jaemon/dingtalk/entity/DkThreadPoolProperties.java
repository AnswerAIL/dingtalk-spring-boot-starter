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
    private static final int DEFAULT_CORE_SIZE = Runtime.getRuntime().availableProcessors() + 1;
    /**
     * 线程池维护线程的最小数量
     */
    private int coreSize = DEFAULT_CORE_SIZE;
    /**
     * 线程池维护线程的最大数量
     */
    private int maxSize = DEFAULT_CORE_SIZE * 2;
    /**
     * 空闲线程的存活时间
     */
    private int keepAliveSeconds = 60;
    /**
     * 持有等待执行的任务队列
     */
    private int queueCapacity = 10;
    /**
     * 线程名称前缀
     */
    private String threadNamePrefix = DEFAULT_THREAD_NAME_PREFIX;
}