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

import com.github.jaemon.dinger.DingerSender;
import com.github.jaemon.dinger.constant.DingerConstant;
import com.github.jaemon.dinger.core.entity.DingerProperties;
import com.github.jaemon.dinger.core.entity.MsgType;
import com.github.jaemon.dinger.entity.DingerResult;
import com.github.jaemon.dinger.support.MonitorEventNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

/**
 * Failed Listener
 *
 * @author Jaemon
 * @since 1.0
 */
public class FailedEventListener implements ApplicationListener<ApplicationFailedEvent> {
    private static final Logger log = LoggerFactory.getLogger(FailedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        boolean debugEnabled = log.isDebugEnabled();

        String monitor = System.getProperty(ApplicationEventTimeTable.DISABLED_DINTALK_MONITOR);
        if (monitor != null && "true".equals(monitor.trim())) {
            return;
        }

        ApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext == null) {
            return;
        }

        if (AnnotationConfigServletWebServerApplicationContext.class.isInstance(applicationContext)
                && ApplicationEventTimeTable.failedTime == 0) {
            if (debugEnabled) {
                log.debug("ready to execute ApplicationFailedEvent.");
            }
            ApplicationEventTimeTable.failedTime = event.getTimestamp();
            DingerProperties properties = applicationContext.getBean(DingerProperties.class);

            if (properties.isEnabled()
                    && properties.getMonitor().isFalied()) {
                DingerSender dingTalkRobot = applicationContext.getBean(DingerSender.class);
                MonitorEventNotification monitorEventNotification = applicationContext.getBean(MonitorEventNotification.class);
                String projectId = properties.getProjectId();
                projectId = projectId == null ? DingerConstant.DINGER_PREFIX : projectId;

                MsgType message = monitorEventNotification.failed(event, projectId);
                String keyword = projectId + DingerConstant.FAILED_KEYWORD;
                DingerResult result = dingTalkRobot.send(keyword, message);
                if (debugEnabled) {
                    log.debug("keyword={}, result={}.", keyword, result.toString());
                }
            }
        }

    }

}