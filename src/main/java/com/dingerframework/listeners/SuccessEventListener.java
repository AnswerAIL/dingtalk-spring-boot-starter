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

import com.dingerframework.DingerSender;
import com.dingerframework.constant.DingerConstant;
import com.dingerframework.core.entity.DingerProperties;
import com.dingerframework.core.entity.MsgType;
import com.dingerframework.support.MonitorEventNotification;
import com.dingerframework.entity.DingerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

/**
 * Success Listener
 *
 * @author Jaemon
 * @since 1.0
 */
public class SuccessEventListener implements ApplicationListener<ApplicationReadyEvent>, Ordered {
    private static final Logger log = LoggerFactory.getLogger(SuccessEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        boolean debugEnabled = log.isDebugEnabled();

        String monitor = System.getProperty(ApplicationEventTimeTable.DISABLED_DINTALK_MONITOR);
        if (monitor != null && "true".equals(monitor.trim())) {
            return;
        }

        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        // fixed #3
        if (/*applicationContext.getParent() == null
                && */AnnotationConfigServletWebServerApplicationContext.class.isInstance(applicationContext)
                && ApplicationEventTimeTable.successTime == 0) {
            if (debugEnabled) {
                log.debug("ready to execute ApplicationReadyEvent.");
            }
            ApplicationEventTimeTable.successTime = event.getTimestamp();

            DingerProperties properties = applicationContext.getBean(DingerProperties.class);

            if (properties.isEnabled()
                    && properties.getMonitor().isSuccess()) {
                DingerSender dingTalkRobot = applicationContext.getBean(DingerSender.class);
                MonitorEventNotification monitorEventNotification = applicationContext.getBean(MonitorEventNotification.class);
                String projectId = properties.getProjectId();
                projectId = projectId == null ? DingerConstant.DINGER_PREFIX : projectId;

                MsgType message = monitorEventNotification.success(event, projectId);
                String keyword = projectId + DingerConstant.SUCCESS_KEYWORD;
                DingerResult result = dingTalkRobot.send(keyword, message);
                if (debugEnabled) {
                    log.debug("keyword={}, result={}.", keyword, result.toString());
                }
            }

        } else {
            if (debugEnabled) {
                log.debug("dingtalk success listener skip, context={}.", applicationContext.getClass().getName());
            }
        }

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}