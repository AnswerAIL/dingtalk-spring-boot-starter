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

import com.dingerframework.core.entity.MsgType;
import com.dingerframework.dingtalk.entity.DingText;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;

/**
 * Notification
 *
 * @author Jaemon
 * @since 1.0
 */
public abstract class MonitorEventNotification {

    /**
     * success Message
     *
     * @param event {@link ApplicationReadyEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message
     */
    public abstract String successMessage(ApplicationReadyEvent event, String projectId);

    /**
     * notification of success
     *
     * @param event {@link ApplicationReadyEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message obj
     */
    public MsgType success(ApplicationReadyEvent event, String projectId) {
        String message = successMessage(event, projectId);
        return message(message);
    };


    /**
     * failed Message
     *
     * @param event {@link ApplicationFailedEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message
     */
    public abstract String failedMessage(ApplicationFailedEvent event, String projectId);

    /**
     * notification of failed
     *
     * @param event {@link ApplicationFailedEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message obj
     */
    public MsgType failed(ApplicationFailedEvent event, String projectId) {
        String message = failedMessage(event, projectId);
        return message(message);
    }

    /**
     * exit Message
     *
     * @param event {@link ContextClosedEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message
     */
    public abstract String exitMessage(ContextClosedEvent event, String projectId);

    /**
     * notification of exit
     *
     * @param event {@link ContextClosedEvent}
     * @param projectId DingTalkProperties#projectId
     * @return message obj
     */
    public MsgType exit(ContextClosedEvent event, String projectId) {
        String message = exitMessage(event, projectId);
        return message(message);
    }

    /**
     * message(default)
     *
     * @param text
     *          消息内容
     * @return
     *          具体消息对象
     */
    public MsgType message(String text) {
        return new DingText(new DingText.Text(text));
    }
}