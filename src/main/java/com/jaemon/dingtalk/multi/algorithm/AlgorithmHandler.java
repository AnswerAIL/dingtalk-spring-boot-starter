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
 * @since 3.0
 */
public interface AlgorithmHandler {
    /** 默认索引号从0开始 */
    int DEFAULT_INDEX = 0;

    /**
     * 具体算法处理逻辑
     *
     * @param dingerConfigs         dingerConfigs
     * @param defaultDingerConfig   defaultDingerConfig
     * @return dingerConfig {@link DingerConfig}
     * */
    DingerConfig handler(List<DingerConfig> dingerConfigs, DingerConfig defaultDingerConfig);

    /**
     * dingerConfig
     *
     * @param dingerConfigs         dingerConfigs
     * @param defaultDingerConfig   defaultDingerConfig
     * @return dingerConfig {@link DingerConfig}
     * */
    default DingerConfig dingerConfig(List<DingerConfig> dingerConfigs, DingerConfig defaultDingerConfig) {
        if (dingerConfigs == null || dingerConfigs.isEmpty()) {
            return defaultDingerConfig;
        }

        if (dingerConfigs.size() == 1) {
            return dingerConfigs.get(0);
        }

        return handler(dingerConfigs, defaultDingerConfig);
    }

    /**
     * 算法ID
     *
     * @return algorithmId
     */
    default String algorithmId() {
        return this.getClass().getSimpleName();
    }
}