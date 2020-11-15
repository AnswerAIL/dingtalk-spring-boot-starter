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
import com.jaemon.dingtalk.utils.RandomUtils;

import java.util.List;

/**
 * 随机选择算法
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 3.0
 */
public class RandomHandler implements AlgorithmHandler {
    @Override
    public DingerConfig execute(List<DingerConfig> dingerConfigs) {
        int size = dingerConfigs.size();
        int index = RandomUtils.nextInt(size);
        return dingerConfigs.get(index);
    }
}