/*
 * Copyright ©2015-2022 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.multi.entity;

import com.github.jaemon.dinger.constant.DingerConstant;
import com.github.jaemon.dinger.core.DingerConfig;
import com.github.jaemon.dinger.multi.algorithm.AlgorithmHandler;

import java.util.List;

/**
 * MultiDingerAlgorithmDefinition
 *
 * @author Jaemon
 * @since 1.0
 */
public class MultiDingerAlgorithmDefinition {
    /**
     * dingerClass + {@link DingerConstant#SPOT_SEPERATOR} + {@link AlgorithmHandler}.simpleName
     */
    private String key;
    /**
     * 算法处理类 {@link AlgorithmHandler}
     *
     * <br>
     * <code>
     *     // 父类.class.isAssignableFrom(子类.class) <br>
     *     AlgorithmHandler.class.isAssignableFrom(algorithm) = true
     * </code>
     */
    private Class<? extends AlgorithmHandler> algorithm;
    /**
     * 有效的钉钉机器人配置集合
     */
    private List<DingerConfig> dingerConfigs;
    /** handler name */
    private String dingerConfigHandlerClassName;

    public MultiDingerAlgorithmDefinition(
            String key, Class<? extends AlgorithmHandler> algorithm,
            List<DingerConfig> dingerConfigs, String dingerConfigHandlerClassName
    ) {
        this.key = key;
        this.algorithm = algorithm;
        this.dingerConfigs = dingerConfigs;
        this.dingerConfigHandlerClassName = dingerConfigHandlerClassName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Class<? extends AlgorithmHandler> getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Class<? extends AlgorithmHandler> algorithm) {
        this.algorithm = algorithm;
    }

    public List<DingerConfig> getDingerConfigs() {
        return dingerConfigs;
    }

    public void setDingerConfigs(List<DingerConfig> dingerConfigs) {
        this.dingerConfigs = dingerConfigs;
    }

    public String getDingerConfigHandlerClassName() {
        return dingerConfigHandlerClassName;
    }

    public void setDingerConfigHandlerClassName(String dingerConfigHandlerClassName) {
        this.dingerConfigHandlerClassName = dingerConfigHandlerClassName;
    }
}