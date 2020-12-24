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
package com.github.jaemon.dinger.multi.algorithm;

import com.github.jaemon.dinger.core.DingerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 轮询算法
 *
 * @author Jaemon
 * @since 1.0
 */
public class RoundRobinHandler implements AlgorithmHandler {
    private static final Logger log = LoggerFactory.getLogger(RoundRobinHandler.class);
    /** 索引值 */
    private volatile int index = DEFAULT_INDEX;

    @Override
    public DingerConfig handler(List<DingerConfig> dingerConfigs, DingerConfig defaultDingerConfig) {
        int size = dingerConfigs.size();
        int idx = index;

        synchronized (this) {
            index++;
            index = index >= size ? DEFAULT_INDEX : index;

            if (log.isDebugEnabled()) {
                log.debug("#{}# 当前使用第{}个机器人", algorithmId(), idx);
            }
//            System.out.println(String.format("#%s# 当前使用第%d个机器人", algorithmId(), idx));
        }

        return dingerConfigs.get(idx);
    }
}