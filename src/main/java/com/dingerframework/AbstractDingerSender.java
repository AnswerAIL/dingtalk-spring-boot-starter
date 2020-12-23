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
package com.dingerframework;

import com.dingerframework.core.DingerHelper;
import com.dingerframework.core.entity.DingerProperties;
import com.dingerframework.core.entity.enums.MessageSubType;
import com.dingerframework.exception.DingerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dingerframework.entity.DingerCallback;
import com.dingerframework.support.CustomMessage;

/**
 * AbstractDingTalkSender
 *
 * @author Jaemon
 * @since 1.0
 */
public abstract class AbstractDingerSender
        extends DingerHelper
        implements DingerSender {

    DingerProperties dingerProperties;
    DingerManagerBuilder dingTalkManagerBuilder;
    ObjectMapper objectMapper;

    public AbstractDingerSender(DingerProperties dingerProperties, DingerManagerBuilder dingTalkManagerBuilder, ObjectMapper objectMapper) {
        this.dingerProperties = dingerProperties;
        this.dingTalkManagerBuilder = dingTalkManagerBuilder;
        this.objectMapper = objectMapper;
    }

    /**
     * 消息类型校验
     *
     * @param messageSubType
     *              消息类型
     * @return
     *              消息生成器
     */
    CustomMessage customMessage(MessageSubType messageSubType) {
        return messageSubType == MessageSubType.TEXT ? dingTalkManagerBuilder.textMessage : dingTalkManagerBuilder.markDownMessage;
    }

    /**
     * 异常回调
     *
     * @param keyword keyword
     * @param message message
     * @param ex ex
     */
    void exceptionCallback(String dingerId, String keyword, String message, DingerException ex) {
        DingerCallback dkExCallable = DingerCallback.builder()
                .dkid(dingerId)
                .dingerProperties(dingerProperties)
                .keyword(keyword)
                .message(message)
                .ex(ex).build();
        dingTalkManagerBuilder.dingerExceptionCallback.execute(dkExCallable);
    }
}