/*
 * Copyright 2015-2020 Jaemon(answer_ljm@163.com)
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
package com.jaemon.dingtalk.multi.algorithm;

import com.jaemon.dingtalk.dinger.DingerConfig;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Dinger钉钉机器人算法
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 3.0
 */
public class DingerHandler implements AlgorithmHandler {
    private static final String SECONDS_KEY = "DINGER_SECONDS";
    private static final String COUNT_KEY = "DINGER_COUNT";
    private static final long DEFAULT_STARTTIME = -1;

    /** SECONDS 秒内限制发送 COUNT 条 */
    private static final int SECONDS_THRESHOLD;
    private static final int COUNT_THRESHOLD;

    static {
        SECONDS_THRESHOLD = System.getProperty(SECONDS_KEY) == null ?
                60 : Integer.parseInt(
                        System.getProperty(SECONDS_KEY)
        );
        COUNT_THRESHOLD = System.getProperty(COUNT_KEY) == null ?
                20 : Integer.parseInt(
                        System.getProperty(COUNT_KEY)
        );
    }

    /** 索引号 */
    private volatile int index = DEFAULT_INDEX;
    /** 初次使用时间 */
    private volatile long startTime = DEFAULT_STARTTIME;
    /** 计数器 */
    private AtomicInteger counter = new AtomicInteger(DEFAULT_INDEX);


    @Override
    public DingerConfig execute(List<DingerConfig> dingerConfigs) {
        int size = dingerConfigs.size();
        long current = System.currentTimeMillis();

        synchronized (this) {
            int count = counter.getAndIncrement();
            if (count >= COUNT_THRESHOLD
                    && (current - startTime) / 1000 <= SECONDS_THRESHOLD) {
                startTime = current;
                counter.set(DEFAULT_INDEX);
                index++;
                index = index >= size ? DEFAULT_INDEX : index;
            }

            if (startTime == DEFAULT_STARTTIME) {
                startTime = System.currentTimeMillis();
            }
        }

        return dingerConfigs.get(index);
    }
}