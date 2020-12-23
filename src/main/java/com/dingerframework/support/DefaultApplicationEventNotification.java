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
package com.dingerframework.support;

import com.dingerframework.listeners.ApplicationEventTimeTable;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * Default Application Event Notification
 *
 * @author Jaemon
 * @since 1.0
 */
public class DefaultApplicationEventNotification extends MonitorEventNotification {
    @Override
    public String successMessage(ApplicationReadyEvent event, String projectId) {
        long startUpTime = (event.getTimestamp() - ApplicationEventTimeTable.startTime()) / 1000;
        String message = MessageFormat.format(
                "服务[{0}]启动成功, 整个启动过程共历时{1}秒.",
                projectId,
                startUpTime);
        return message;
    }

    @Override
    public String failedMessage(ApplicationFailedEvent event, String projectId) {
        String message = MessageFormat.format(
                "服务[{0}]启动失败, 失败原因为[{1}].",
                projectId,
                Optional
                        .ofNullable(event)
                        .map(ApplicationFailedEvent::getException)
                        .map(Throwable::getCause)
                        .map(Throwable::getMessage)
                        .orElse(event.getException().getMessage()));
        return message;
    }

    @Override
    public String exitMessage(ContextClosedEvent event, String projectId) {
        long runningTime = 0;
        if (ApplicationEventTimeTable.successTime() > 0) {
            runningTime = (event.getTimestamp() - ApplicationEventTimeTable.successTime()) / 1000;
        }
        String message = MessageFormat.format(
                "服务[{0}]已退出, 服务稳定运行时长为{1}秒.",
                projectId,
                runningTime);
        return message;
    }

}