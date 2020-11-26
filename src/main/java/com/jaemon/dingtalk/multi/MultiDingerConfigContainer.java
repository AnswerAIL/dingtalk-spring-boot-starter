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

import com.jaemon.dingtalk.multi.entity.MultiDingerConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * MultiDingerConfigContainer
 *
 * @author Jaemon#answer_ljm@163.com
 * @since 3.0
 */
public enum MultiDingerConfigContainer {
    /**
     * instance
     */
    INSTANCE;

    /** 全局 MultiDingerConfig key */
    public static final String GLOABL_KEY = MultiDingerConfigContainer.class.getName();
    /**
     * <blockquote>
     *     { <br>
     *         key: dingclasssName | GLOABL_KEY <br>
     *         value: {@link MultiDingerConfig} <br>
     *     } <br>
     * </blockquote>
     */
    private Map<String, MultiDingerConfig> container;

    MultiDingerConfigContainer() {
        this.container = new HashMap<>();
    }

    /**
     * @param key
     *              DingerClass类名 | {@link MultiDingerConfigContainer#GLOABL_KEY}
     * @param multiDingerConfig
     *              {@link MultiDingerConfig}
     * @return
     *              {@link MultiDingerConfig}
     */
    public MultiDingerConfig put(String key, MultiDingerConfig multiDingerConfig) {
        return this.container.put(key, multiDingerConfig);
    }

    public boolean isEmpty() {
        return this.container.isEmpty();
    }

    public MultiDingerConfig get(String key) {
        if (this.container.containsKey(key)) {
            return this.container.get(key);
        }
        return this.container.get(GLOABL_KEY);
    }
}