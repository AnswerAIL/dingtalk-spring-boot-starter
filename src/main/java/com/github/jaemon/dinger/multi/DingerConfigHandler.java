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
package com.github.jaemon.dinger.multi;

import com.github.jaemon.dinger.core.DingerConfig;
import com.github.jaemon.dinger.multi.algorithm.AlgorithmHandler;
import com.github.jaemon.dinger.multi.algorithm.DefaultHandler;
import com.github.jaemon.dinger.multi.algorithm.DingerHandler;
import com.github.jaemon.dinger.multi.annotations.MultiDinger;

import java.util.List;

/**
 * DingerConfigHandler
 *
 * @author Jaemon
 * @since 1.0
 */
public interface DingerConfigHandler {

    /**
     * 多Dinger机器人配置
     *
     * <pre>
     *     1. DingerConfig中的DingerType统一使用 {@link MultiDinger#dinger()} 中指定的
     *     2. dingerConfigs的配置信息必须是 {@link MultiDinger#dinger()} 指定的机器人配置信息
     * </pre>
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
        return DingerHandler.class;
    }

}