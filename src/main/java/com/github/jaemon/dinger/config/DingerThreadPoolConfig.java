/*
 * Copyright ©2015-2021 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.config;

import com.github.jaemon.dinger.core.DingerRobot;
import com.github.jaemon.dinger.constant.DingerConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * DINGTALK线程池配置类
 *
 * @author Jaemon
 * @since 1.0
 */
@Configuration
@ConditionalOnProperty(
        prefix = DingerConstant.DINGER_PROP_PREFIX,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@ConditionalOnBean(DingerRobot.class)
//@Conditional(AsyncCondition.class)
@ConditionalOnMissingBean(name = DingerConstant.DINGER_EXECUTOR)
@EnableConfigurationProperties({DingerThreadPoolProperties.class})
public class DingerThreadPoolConfig {


    @Bean(name = DingerConstant.DINGER_EXECUTOR)
    public Executor dingTalkExecutor(DingerThreadPoolProperties dingerThreadPoolProperties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(dingerThreadPoolProperties.getCoreSize());
        // 最大线程数
        executor.setMaxPoolSize(dingerThreadPoolProperties.getMaxSize());
        // 线程最大空闲时间
        executor.setKeepAliveSeconds(dingerThreadPoolProperties.getKeepAliveSeconds());
        // 队列大小
        executor.setQueueCapacity(dingerThreadPoolProperties.getQueueCapacity());
        // 指定用于新创建的线程名称的前缀
        executor.setThreadNamePrefix(dingerThreadPoolProperties.getThreadNamePrefix());

        // 使用自定义拒绝策略, 直接抛出异常
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 等待任务完成时再关闭线程池--表明等待所有线程执行完
        executor.setWaitForTasksToCompleteOnShutdown(true);

        // 初始化
        executor.initialize();
        return executor;
    }

}