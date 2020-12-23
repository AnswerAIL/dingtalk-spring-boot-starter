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
package com.dingerframework.entity;

import com.dingerframework.constant.DkConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DINGTALK线程池参数配置-用于异步处理
 *
 * @author Jaemon
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = DkConstant.DINGER_PROPERTIES_PREFIX + "executor-pool")
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
    private String threadNamePrefix = DkConstant.DEFAULT_THREAD_NAME_PREFIX;
}