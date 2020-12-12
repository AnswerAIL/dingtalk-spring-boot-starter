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
package com.jaemon.dingtalk.listeners;

import com.jaemon.dingtalk.dinger.annatations.DingerScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.ApplicationListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Start Listener
 *
 * @author Jaemon
 * @since 1.0
 */
public class StartEventListener implements ApplicationListener<ApplicationStartingEvent> {
    private static final Logger log = LoggerFactory.getLogger(StartEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        if (ApplicationEventTimeTable.startTime > 0) {
            log.info("dingtalk has already been initialized.");
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("ready to execute ApplicationStartingEvent.");
        }
        ApplicationEventTimeTable.startTime = event.getTimestamp();

        Set<Object> allSources = event.getSpringApplication().getAllSources();
        Set<Class<?>> primarySources = new HashSet<>();
        for (Object source : allSources) {
            if (Class.class.isInstance(source)) {
                Class<?> clazz = (Class<?>) source;
                if (clazz.isAnnotationPresent(DingerScan.class)) {
                    primarySources.add(clazz);
                }
            }
        }
        ApplicationEventTimeTable.primarySources = primarySources;

        ApplicationHome applicationHome = new ApplicationHome();
        ApplicationEventTimeTable.applicationHome = applicationHome;
        log.info("applicationHome={}", applicationHome.toString());
    }
}