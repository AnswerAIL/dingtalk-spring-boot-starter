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

/**
 * 轮询算法
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 3.0
 */
public class RoundRobinHandler implements AlgorithmHandler {
    /** 索引值 */
    private volatile int index = DEFAULT_INDEX;

    @Override
    public DingerConfig execute(List<DingerConfig> dingerConfigs) {
        int size = dingerConfigs.size();
        int idx = index;

        synchronized (this) {
            index++;
            index = index >= size ? DEFAULT_INDEX : index;
        }

        return dingerConfigs.get(idx);
    }
}