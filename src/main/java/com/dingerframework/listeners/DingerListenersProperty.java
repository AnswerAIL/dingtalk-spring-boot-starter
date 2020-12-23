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
package com.dingerframework.listeners;

import com.dingerframework.core.entity.enums.DingerType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ApplicationEventTimeTable
 *
 * @author Jaemon
 * @since 4.0
 */
public class DingerListenersProperty {
    /**
     * primarySources
     */
    static Set<Class<?>> primarySources = new HashSet<>();
    /**
     * dingerClasses
     */
    static List<Class<?>> dingerClasses = new ArrayList<>();

    protected static Set<Class<?>> primarySources() {
        return primarySources;
    }

    protected static List<Class<?>> dingerClasses() {
        return dingerClasses;
    }

    protected static void emptyDingerClasses() {
        if (dingerClasses != null && !dingerClasses.isEmpty()) {
            dingerClasses.clear();
        }
    }

    protected final static List<DingerType> enabledDingerTypes;
    static {
        enabledDingerTypes = Arrays.stream(DingerType.values()).filter(e -> e.isEnabled()).collect(Collectors.toList());
    }

}