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
package com.github.jaemon.dinger.listeners;


import java.util.ArrayList;
import java.util.HashSet;

/**
 * ApplicationEventTimeTable
 *
 * @author Jaemon
 * @since 1.0
 */
public final class ApplicationEventTimeTable extends DingerListenersProperty {
    /**
     * 禁用dinger监控功能
     *
     * -Ddinger.monitor.disabled=true
     * */
    static final String DISABLED_DINTALK_MONITOR = "dinger.monitor.disabled";
    /**
     * startTime
     */
    static long startTime = 0;
    /**
     * successTime
     */
    static long successTime = 0;
    /**
     * failedTime
     */
    static long failedTime = 0;
    /**
     * exitTime
     */
    static long exitTime = 0;

    private ApplicationEventTimeTable() {
    }

    public static long startTime() {
        return startTime;
    }

    public static long successTime() {
        return successTime;
    }

    public static long failedTime() {
        return failedTime;
    }

    public static long exitTime() {
        return exitTime;
    }


    protected static void clear() {
        ApplicationEventTimeTable.startTime = 0;
        ApplicationEventTimeTable.successTime = 0;
        ApplicationEventTimeTable.failedTime = 0;
        ApplicationEventTimeTable.exitTime = 0;
        primarySources = new HashSet<>();
        dingerClasses = new ArrayList<>();
    }

}