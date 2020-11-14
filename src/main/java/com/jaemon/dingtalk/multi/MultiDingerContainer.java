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

import java.util.ArrayList;
import java.util.List;

/**
 * MultiDingerContainer
 *
 * @author Jaemon#answer_ljm@163.com
 * @version 3.0
 */
public enum MultiDingerContainer {
    INSTANCE;

    private List<Class<? extends DingerConfigHandler>> dingerConfigHandlerClasses;

    MultiDingerContainer() {
        this.dingerConfigHandlerClasses = new ArrayList<>();
    }

    public boolean add(Class<? extends DingerConfigHandler> dingerConfigHandlerClass) {
        return this.dingerConfigHandlerClasses.add(dingerConfigHandlerClass);
    }

    public boolean isEmpty() {
        return this.dingerConfigHandlerClasses.isEmpty();
    }

    public List<Class<? extends DingerConfigHandler>> dingerConfigHandlerClasses() {
        return this.dingerConfigHandlerClasses;
    }

}