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
package com.jaemon.dingtalk.multi;

import com.jaemon.dingtalk.dinger.DingerConfig;

import java.util.List;

/**
 * DingerConfigHandler
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 3.0
 */
public interface DingerConfigHandler {

    /**
     * dingerConfigs
     *
     * @return dingerConfigs
     * */
    List<DingerConfig> dingerConfigs();

    /**
     * algorithm
     *
     * @return algorithmType {@link AlgorithmType}
     * */
    default AlgorithmType algorithm() {
        return AlgorithmType.ROUND_ROBIN;
    }

    /**
     * global
     *
     * @return boolean
     *
     * */
    default boolean global() {
        return false;
    }
}