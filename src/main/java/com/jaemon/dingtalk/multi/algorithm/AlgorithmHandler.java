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
 * AlgorithmHandler
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 3.0
 */
public interface AlgorithmHandler {
    int DEFAULT_INDEX = 0;

    /**
     * execute
     *
     * @param dingerConfigs dingerConfigs
     * @return dingerConfig {@link DingerConfig}
     * */
    DingerConfig execute(List<DingerConfig> dingerConfigs);

    /**
     * dingerConfig
     *
     * @param dingerConfigs dingerConfigs
     * @return dingerConfig {@link DingerConfig}
     * */
    default DingerConfig dingerConfig(List<DingerConfig> dingerConfigs) {
        if (dingerConfigs == null || dingerConfigs.isEmpty()) {
            return null;
        }

        return execute(dingerConfigs);
    }
}