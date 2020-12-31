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
package com.github.jaemon.dinger.multi.entity;

import com.github.jaemon.dinger.multi.DingerConfigHandler;

/**
 * MultiDinger
 *
 * @author Jaemon
 * @since 1.0
 */
public class MultiDinger {
    private String key;
    private Class<? extends DingerConfigHandler> dingerConfigHandler;

    public MultiDinger(String key, Class<? extends DingerConfigHandler> dingerConfigHandler) {
        this.key = key;
        this.dingerConfigHandler = dingerConfigHandler;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Class<? extends DingerConfigHandler> getDingerConfigHandler() {
        return dingerConfigHandler;
    }

    public void setDingerConfigHandler(Class<? extends DingerConfigHandler> dingerConfigHandler) {
        this.dingerConfigHandler = dingerConfigHandler;
    }
}