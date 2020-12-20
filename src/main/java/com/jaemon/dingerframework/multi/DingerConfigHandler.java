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
package com.jaemon.dingerframework.multi;

import com.jaemon.dingerframework.dingtalk.DingerConfig;
import com.jaemon.dingerframework.multi.algorithm.AlgorithmHandler;
import com.jaemon.dingerframework.multi.algorithm.DefaultHandler;

import java.util.List;

/**
 * DingerConfigHandler
 *
 * @author Jaemon
 * @since 3.0
 */
public interface DingerConfigHandler {

    /**
     * 多钉钉机器人配置
     *
     * @return dingerConfigs
     * */
    List<DingerConfig> dingerConfigs();

    /**
     * 执行逻辑处理器
     *
     * <blockquote>
     *     default algorithmHandler {@link DefaultHandler}
     * </blockquote>
     *
     * @return algorithmHandler {@link AlgorithmHandler}
     * */
    default Class<? extends AlgorithmHandler> algorithmHandler() {
        return DefaultHandler.class;
    }

}