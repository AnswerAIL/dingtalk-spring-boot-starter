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
package com.github.jaemon.dinger.multi;

import com.github.jaemon.dinger.core.entity.enums.IgnoreMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * MultiDingerProperty
 *
 * @author Jaemon
 * @since 1.0
 */
public class MultiDingerProperty {
    /** app start at multiDinger */
    static boolean multiDinger = false;
    protected static Map<String, IgnoreMethod> ignoreMethodMap = new HashMap<>();

    static {
        for (
                IgnoreMethod ignoreMethod : IgnoreMethod.values()
        ) {
            ignoreMethodMap.put(ignoreMethod.getMethodName(), ignoreMethod);
        }
    }

    protected static boolean multiDinger() {
        return multiDinger;
    }

    protected static void clear() {
        ignoreMethodMap.clear();
    };
}