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
package com.jaemon.dingerframework.listeners;

import com.jaemon.dingerframework.DingerSender;
import com.jaemon.dingerframework.entity.DingerProperties;
import com.jaemon.dingerframework.entity.DingTalkResult;
import com.jaemon.dingerframework.entity.message.MsgType;
import com.jaemon.dingerframework.multi.MultiDingerRefresh;
import com.jaemon.dingerframework.support.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import static com.jaemon.dingerframework.constant.DkConstant.DK_PREFIX;
import static com.jaemon.dingerframework.constant.DkConstant.EXIT_KEYWORD;
import static com.jaemon.dingerframework.listeners.ApplicationEventTimeTable.DISABLED_DINTALK_MONITOR;

/**
 * Exit Listener
 *
 * @author Jaemon
 * @since 1.0
 */
public class ExitEventListener
        extends MultiDingerRefresh
        implements ApplicationListener<ContextClosedEvent> {
    private static final Logger log = LoggerFactory.getLogger(ExitEventListener.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        boolean debugEnabled = log.isDebugEnabled();

        try {
            String monitor = System.getProperty(DISABLED_DINTALK_MONITOR);
            if (monitor != null && "true".equals(monitor.trim())) {
                return;
            }

            ApplicationContext applicationContext = event.getApplicationContext();

            if (AnnotationConfigServletWebServerApplicationContext.class.isInstance(applicationContext)
                    && ApplicationEventTimeTable.exitTime == 0) {
                if (debugEnabled) {
                    log.debug("ready to execute ContextClosedEvent.");
                }
                ApplicationEventTimeTable.exitTime = event.getTimestamp();
                DingerProperties properties = applicationContext.getBean(DingerProperties.class);

                if (properties.isEnabled()
                        && properties.getMonitor().isExit()
                        // exclude start-up failed
                        && ApplicationEventTimeTable.successTime() > 0) {
                    DingerSender dingTalkRobot = applicationContext.getBean(DingerSender.class);
                    Notification notification = applicationContext.getBean(Notification.class);
                    String projectId = properties.getProjectId();
                    projectId = projectId == null ? DK_PREFIX : projectId;

                    MsgType message = notification.exit(event, projectId);
                    String keyword = projectId + EXIT_KEYWORD;
                    DingTalkResult result = dingTalkRobot.send(keyword, message);
                    if (debugEnabled) {
                        log.debug("keyword={}, result={}.", keyword, result.toString());
                    }
                }
            }
        } finally {
            // support devtools
            refresh();
        }

    }

    private void refresh() {
        multiDingerRefresh();
        ApplicationEventTimeTable.clear();
    }
}