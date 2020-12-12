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
package com.jaemon.dingtalk.multi.algorithm;

import com.jaemon.dingtalk.dinger.DingerConfig;

import java.util.List;

/**
 * AlgorithmHandler
 *
 * @author Jaemon
 * @since 3.0
 */
public interface AlgorithmHandler {
    /** 默认索引号从0开始 */
    int DEFAULT_INDEX = 0;
    /**
     * {@link com.jaemon.dingtalk.multi.MultiDingerAlgorithmRegister}
     */
    String MULTI_DINGER_PRIORITY_EXECUTE = "multiDingerAlgorithmRegister";

    /**
     * 具体算法处理逻辑
     *
     * @param dingerConfigs
     *              多钉钉机器人配置集
     * @param defaultDingerConfig
     *              默认钉钉机器人配置，即： 未开启MultiDinger时使用的机器人配置
     * @return dingerConfig
     *              返回当前应该使用的钉钉机器人配置{@link DingerConfig}
     * */
    DingerConfig handler(List<DingerConfig> dingerConfigs, DingerConfig defaultDingerConfig);

    /**
     * dingerConfig
     *
     * @param dingerConfigs
     *              多钉钉机器人配置集
     * @param defaultDingerConfig
     *              默认钉钉机器人配置，即： 未开启MultiDinger时使用的机器人配置
     * @return dingerConfig
     *              返回当前应该使用的钉钉机器人配置{@link DingerConfig}
     * */
    default DingerConfig dingerConfig(List<DingerConfig> dingerConfigs, DingerConfig defaultDingerConfig) {
        if (dingerConfigs == null || dingerConfigs.isEmpty()) {
            return defaultDingerConfig;
        }

        // 如果只配置一个，则不需要执行算法处理逻辑
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