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
package com.github.jaemon.dinger.multi.algorithm;

import com.github.jaemon.dinger.core.DingerConfig;
import com.github.jaemon.dinger.utils.RandomUtils;

import java.util.List;

/**
 * 随机选择算法
 *
 * @author Jaemon
 * @since 1.0
 */
public class RandomHandler implements AlgorithmHandler {
    @Override
    public DingerConfig handler(List<DingerConfig> dingerConfigs, DingerConfig defaultDingerConfig) {
        int size = dingerConfigs.size();
        int index = RandomUtils.nextInt(size);
        return dingerConfigs.get(index);
    }
}